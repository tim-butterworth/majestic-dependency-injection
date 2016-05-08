package com.reflecty.instantiators;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.configurations.DecoratedClass;
import com.reflecty.helperObjects.ObjectContainer;
import com.reflecty.testModels.RandomSingletonStringInterface;
import com.reflecty.testModels.RandomStringInterface;
import com.reflecty.testModels.StatefulObject;
import com.reflecty.testModels.StatefulObjectProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProxiedInterfaceInstantiatorTest {

    private ProxiedInterfaceInstantiator proxiedInterfaceInstantiator;
    private ObjectBuilderMachine objectBuilderMachine;

    @Before
    public void setUp() throws Exception {
        objectBuilderMachine = mock(ObjectBuilderMachine.class);
        ObjectContainer<ObjectBuilderMachine> builderMachineContainer = new ObjectContainer<>();
        builderMachineContainer.addToContainer(objectBuilderMachine);

        proxiedInterfaceInstantiator = new ProxiedInterfaceInstantiator(builderMachineContainer);
    }

    @Test
    public void instantiate_randomAnnotatedWithStringMethod() throws Exception {
        RandomStringInterface instantiate = proxiedInterfaceInstantiator.instantiate(new DecoratedClass<>(RandomStringInterface.class));

        assertThat(instantiate.getAString(), not(nullValue()));
    }

    @Test
    public void instantiate_providersProvideObjects() throws Exception {
        StatefulObject expectedStatefulObject = new StatefulObject();
        when(objectBuilderMachine.getInstance(StatefulObject.class)).thenReturn(expectedStatefulObject);

        StatefulObjectProvider statefulObjectProvider = proxiedInterfaceInstantiator.instantiate(new DecoratedClass<>(StatefulObjectProvider.class));

        StatefulObject instance = statefulObjectProvider.getInstance();

        assertThat(instance, sameInstance(expectedStatefulObject));

        verify(objectBuilderMachine).getInstance(StatefulObject.class);
    }
}