package com.reflecty.instantiators;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.configurations.DecoratedClass;
import com.reflecty.helperObjects.ObjectContainer;
import com.reflecty.testModels.*;
import com.reflecty.testModels.proxiedModels.WrappedMethodCall;
import com.reflecty.testModels.proxiedModels.WrappedMethodCallConstructorImpl;
import com.reflecty.testModels.proxiedModels.WrappedMethodCallImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProxiedClassInstantiatorTest {

    private ProxiedClassInstantiator proxiedClassInstantiator;
    private ObjectBuilderMachine objectBuilderMachine;

    @Before
    public void setUp() throws Exception {
        objectBuilderMachine = mock(ObjectBuilderMachine.class);

        ObjectContainer<ObjectBuilderMachine> objectContainer = new ObjectContainer<>();
        objectContainer.addToContainer(objectBuilderMachine);

        CreateObjectFromConstructor createObjectFromConstructor = new CreateObjectFromConstructor(objectContainer);
        proxiedClassInstantiator = new ProxiedClassInstantiator(createObjectFromConstructor);
    }

    @Test
    public void instantiate_returnsAProxyWrappedClass() throws Exception {
        WrappedMethodCall instance = proxiedClassInstantiator.instantiate(new DecoratedClass<>(WrappedMethodCallImpl.class), new HashSet<>());

        ConfigureableObject configureableObject = new ConfigureableObject();
        instance.doTheThing(configureableObject);

        configurationAssertions(configureableObject);
    }

    @Test
    public void instantiate_returnsAProxyWrappedClassForAClassWithAConstructor() throws Exception {
        TestClass1 expectedClass = new TestClass1();
        HashSet<Class<?>> classes = new HashSet<>();
        when(objectBuilderMachine.getInstance(TestClass1.class, classes)).thenReturn(expectedClass);

        WrappedMethodCall instance = proxiedClassInstantiator.instantiate(new DecoratedClass<>(WrappedMethodCallConstructorImpl.class), classes);

        ConfigureableObject configureableObject = new ConfigureableObject();
        instance.doTheThing(configureableObject);

        configurationAssertions(configureableObject);

        assertThat(instance.getTestClass(), sameInstance(expectedClass));
    }

    private void configurationAssertions(ConfigureableObject configureableObject) {
        assertThat(configureableObject.getStringFromAfter(), is("This string was added after the method invocation."));
        assertThat(configureableObject.getStringFromBefore(), is("Setting the string to be before the method invocation"));
        assertThat(configureableObject.getStringFromMethod(), is("This string comes from the method call"));
    }
}