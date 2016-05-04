package com.reflecty.creators;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.configurations.InterfaceModule;
import com.reflecty.configurations.DecoratedClass;
import com.reflecty.helperObjects.ObjectContainer;
import com.reflecty.instantiators.ReflectiveInstantiator;
import com.reflecty.testModels.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;

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
    private InterfaceModule module;

    @Before
    public void setUp() throws Exception {
        ObjectContainer<ObjectBuilderMachine> container = new ObjectContainer<>();
        builderMachine = mock(ObjectBuilderMachine.class);
        container.addToContainer(builderMachine);

        module = mock(InterfaceModule.class);

        reflectiveInstantiator = new ReflectiveInstantiator(
                container
        );
    }

    @Test
    public void instantiate_worksForDefaultConstructors() throws Exception {
        final DecoratedClass<TestClass1> classContainer = new DecoratedClass<>(TestClass1.class);
        TestClass1 instantiate = reflectiveInstantiator.instantiate(classContainer);

        assertThat(instantiate, not(nullValue()));
    }

    @Test
    public void instantiate_worksForConstructorWithParameters() throws Exception {
        when(builderMachine.getInstance(SingleTonClass.class)).thenReturn(new SingleTonClass());

        final DecoratedClass<ConstructorTonClass> classContainer = new DecoratedClass<>(ConstructorTonClass.class);
        ConstructorTonClass instantiate = reflectiveInstantiator.instantiate(classContainer);

        assertThat(instantiate, not(nullValue()));

        verify(builderMachine).getInstance(SingleTonClass.class);
    }

    @Test
    public void instantiate_worksRegisteredInterfacesInConstuctorParams() throws Exception {
        Annotation[][] parameterAnnotations = ConstructorWithAnnotatedParams.class.getDeclaredConstructors()[0].getParameterAnnotations();

        when(builderMachine.getInstance(InterfaceForAnObject.class, parameterAnnotations[0])).thenReturn(new ImplOne());
        when(builderMachine.getInstance(InterfaceForAnObject.class, parameterAnnotations[1])).thenReturn(new ImplTwo());

        final DecoratedClass<ConstructorWithAnnotatedParams> classContainer = new DecoratedClass<>(ConstructorWithAnnotatedParams.class);
        ConstructorWithAnnotatedParams instantiate = reflectiveInstantiator.instantiate(classContainer);

        assertThat(instantiate, not(nullValue()));
        assertThat(instantiate.getFirstObj(), instanceOf(ImplOne.class));
        assertThat(instantiate.getSecondObj(), instanceOf(ImplTwo.class));
    }

}