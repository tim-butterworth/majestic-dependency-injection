package com.reflecty;

import com.reflecty.creators.InstanceCreator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class BuildFactoryMachineTest {

    private BuildFactoryMachine buildFactoryMachine;
    private InstanceCreatorFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = mock(InstanceCreatorFactory.class);
        buildFactoryMachine = new BuildFactoryMachine(factory);
    }

    @Test
    public void buildItRealWell_usesTheInstanceCreatorProvidedByTheFactory() throws Exception {
        Class<Object> aClass = Object.class;
        Object expectedObject = new Object();
        InstanceCreator instanceCreator = mock(InstanceCreator.class);

        when(factory.getInstanceCreator(aClass)).thenReturn(instanceCreator);
        when(instanceCreator.getInstance(aClass)).thenReturn(expectedObject);

        Object actualObject = buildFactoryMachine.buildItRealWell(aClass);

        assertThat(actualObject, sameInstance(expectedObject));
        verify(instanceCreator).getInstance(aClass);
    }

}