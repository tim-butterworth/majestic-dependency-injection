package com.reflecty.creators;

import com.reflecty.cachingStrategies.CachingStrategy;
import com.reflecty.configurations.InterfaceModule;
import com.reflecty.configurations.DecoratedClass;
import com.reflecty.instantiators.ReflectiveInstantiator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.TestClass;

import java.util.HashSet;

import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InstanceCreatorMachineTest {

    private InstanceCreatorMachine instanceCreatorMachine;
    private final CachingStrategy cachingStrategy = mock(CachingStrategy.class);
    private final InterfaceModule module = mock(InterfaceModule.class);
    private final ReflectiveInstantiator defaultInstantiator = mock(ReflectiveInstantiator.class);

    @Before
    public void setUp() throws Exception {
        instanceCreatorMachine = new InstanceCreatorMachine();
    }

    @Test
    public void getInstance() throws Exception {
        DecoratedClass<TestClass> decoratedClass = new DecoratedClass<>(TestClass.class);

        TestClass expectedInstance = mock(TestClass.class);

        when(cachingStrategy.getInstance(defaultInstantiator, decoratedClass, new HashSet<>())).thenReturn(expectedInstance);
        when(module.getMatch(decoratedClass)).thenReturn(TestClass.class);

        TestClass instance = instanceCreatorMachine.getInstance(new DecoratedClass<>(TestClass.class), cachingStrategy, defaultInstantiator, new HashSet<>());

        assertThat(instance, sameInstance(expectedInstance));
    }

}