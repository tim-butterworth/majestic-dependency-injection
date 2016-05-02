package com.reflecty.creators;

import com.reflecty.cachingStrategies.CachingStrategy;
import com.reflecty.configurations.BuildModule;
import com.reflecty.configurations.DecoratedClass;
import com.reflecty.instantiators.ReflectiveInstantiator;
import com.reflecty.matchers.Matcher;
import com.sun.xml.internal.rngom.ast.builder.Annotations;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.TestClass;

import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InstanceCreatorMachineTest {

    private InstanceCreatorMachine instanceCreatorMachine;
    private final Matcher matcher = mock(Matcher.class);
    private final CachingStrategy cachingStrategy = mock(CachingStrategy.class);
    private final BuildModule module = mock(BuildModule.class);
    private final ReflectiveInstantiator defaultInstantiator = mock(ReflectiveInstantiator.class);

    @Before
    public void setUp() throws Exception {
        instanceCreatorMachine = new InstanceCreatorMachine(
                defaultInstantiator,
                module,
                cachingStrategy,
                matcher
        );
    }

    @Test
    public void getInstance() throws Exception {
        DecoratedClass<TestClass> decoratedClass = new DecoratedClass<>(TestClass.class);

        TestClass expectedInstance = mock(TestClass.class);

        when(cachingStrategy.getInstance(decoratedClass, defaultInstantiator)).thenReturn(expectedInstance);
        when(module.getMatch(decoratedClass)).thenReturn(TestClass.class);

        TestClass instance = instanceCreatorMachine.getInstance(decoratedClass);

        assertThat(instance, sameInstance(expectedInstance));
    }

    @Test
    public void canHandle() throws Exception {

    }

}