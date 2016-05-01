package com.reflecty.creators;

import com.reflecty.configurations.BuildModule;
import com.reflecty.helperObjects.ObjectContainer;
import com.reflecty.instantiators.ReflectiveInstantiator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

public class DefaultInstanceCreatorTest {

    private DefaultInstanceCreator defaultInstanceCreator;
    private ReflectiveInstantiator reflectiveInstantiator;

    @Before
    public void setUp() throws Exception {
        reflectiveInstantiator = new ReflectiveInstantiator(new ObjectContainer<>(), new BuildModule());
        defaultInstanceCreator = new DefaultInstanceCreator(reflectiveInstantiator);
    }

    @Test
    public void getInstance() throws Exception {
        Object instance = defaultInstanceCreator.getInstance(Object.class);

        assertThat(instance, not(nullValue()));
    }

}