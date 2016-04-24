package com.reflecty.creators;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

public class DefaultInstanceCreatorTest {

    private DefaultInstanceCreator defaultInstanceCreator;

    @Before
    public void setUp() throws Exception {
        defaultInstanceCreator = new DefaultInstanceCreator();
    }

    @Test
    public void getInstance() throws Exception {
        Object instance = defaultInstanceCreator.getInstance(Object.class);

        assertThat(instance, not(nullValue()));
    }

}