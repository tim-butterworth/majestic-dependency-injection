package com.reflecty;

import com.reflecty.creators.DefaultInstanceCreator;
import com.reflecty.creators.InstanceCreator;
import com.reflecty.creators.ReflectiveInstantiator;
import com.reflecty.creators.SingletonInstanceCreator;
import com.reflecty.testModels.NonSingleTonClass;
import com.reflecty.testModels.SingleTonClass;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class InstanceCreatorFactoryTest {

    private InstanceCreatorFactory instanceCreatorFactory;

    @Before
    public void setUp() throws Exception {
        ReflectiveInstantiator reflectiveInstantiator = mock(ReflectiveInstantiator.class);
        instanceCreatorFactory = new InstanceCreatorFactory(reflectiveInstantiator);
    }

    @Test
    public void getInstanceCreator_returnsADefaultInstanceCreatorForNonAnnotatedClasses() throws Exception {
        InstanceCreator instanceCreator = instanceCreatorFactory.getInstanceCreator(NonSingleTonClass.class);

        assertThat((instanceCreator instanceof DefaultInstanceCreator), is(true));
    }

    @Test
    public void getInstanceCreator_returnsASingletonInstanceCreatorForSingletonAnnotatedClasses() throws Exception {
        InstanceCreator instanceCreator = instanceCreatorFactory.getInstanceCreator(SingleTonClass.class);

        assertThat((instanceCreator instanceof SingletonInstanceCreator), is(true));
    }

}