package com.reflecty.creators;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.configurations.BuildModule;
import com.reflecty.configurations.DecoratedClass;
import com.reflecty.configurations.NamespaceTypeMatcherImpl;
import com.reflecty.configurations.TypeMatcherImpl;
import com.reflecty.helperObjects.ObjectContainer;
import com.reflecty.instantiators.ReflectiveInstantiator;
import com.reflecty.testModels.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReflectiveInstantiatorTest {

    private ReflectiveInstantiator reflectiveInstantiator;
    private ObjectBuilderMachine builderMachine;
    private BuildModule module;

    @Before
    public void setUp() throws Exception {
        ObjectContainer<ObjectBuilderMachine> container = new ObjectContainer<>();
        builderMachine = mock(ObjectBuilderMachine.class);
        container.addToContainer(builderMachine);

        module = mock(BuildModule.class);

        reflectiveInstantiator = new ReflectiveInstantiator(
                container,
                module
        );
    }

    @Test
    public void instantiate_worksForDefaultConstructors() throws Exception {
        TestClass1 instantiate = reflectiveInstantiator.instantiate(new DecoratedClass<>(TestClass1.class));

        assertThat(instantiate, not(nullValue()));
    }

    @Test
    public void instantiate_worksForConstructorWithParameters() throws Exception {
        when(builderMachine.getInstance(SingleTonClass.class)).thenReturn(new SingleTonClass());

        ConstructorTonClass instantiate = reflectiveInstantiator.instantiate(new DecoratedClass<>(ConstructorTonClass.class));

        assertThat(instantiate, not(nullValue()));

        verify(builderMachine).getInstance(SingleTonClass.class);
    }

    @Test
    public void instantiate_worksRegisteredInterfaces() throws Exception {
        when(module.<InterfaceForAnObject>getMatch(eq(new TypeMatcherImpl(InterfaceForAnObject.class)))).thenReturn(ImplOne.class);

        InterfaceForAnObject instantiate = reflectiveInstantiator.instantiate(new DecoratedClass<>(InterfaceForAnObject.class));

        assertThat(instantiate, not(nullValue()));
        assertThat(instantiate, instanceOf(ImplOne.class));
    }

    @Test
    public void instantiate_worksRegisteredInterfacesInConstuctorParams() throws Exception {
        when(module.getMatch(eq(new NamespaceTypeMatcherImpl("One", InterfaceForAnObject.class)))).thenReturn(ImplOne.class);
        when(module.getMatch(eq(new NamespaceTypeMatcherImpl("Two", InterfaceForAnObject.class)))).thenReturn(ImplTwo.class);

        when(builderMachine.getInstance(ImplOne.class)).thenReturn(new ImplOne());
        when(builderMachine.getInstance(ImplTwo.class)).thenReturn(new ImplTwo());

        ConstructorWithAnnotatedParams instantiate = reflectiveInstantiator.instantiate(new DecoratedClass<>(ConstructorWithAnnotatedParams.class));

        assertThat(instantiate, not(nullValue()));
        assertThat(instantiate.getFirstObj(), instanceOf(ImplOne.class));
        assertThat(instantiate.getSecondObj(), instanceOf(ImplTwo.class));
    }

}