package com.reflecty.creators;

import com.reflecty.ObjectBuilderMachine;
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
        reflectiveInstantiator = new ReflectiveInstantiator(new ObjectBuilderMachine());
        defaultInstanceCreator = new DefaultInstanceCreator(reflectiveInstantiator);
    }

    @Test
    public void getInstance() throws Exception {
        Object instance = defaultInstanceCreator.getInstance(Object.class);

        assertThat(instance, not(nullValue()));
    }

}