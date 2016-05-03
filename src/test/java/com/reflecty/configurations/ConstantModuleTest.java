package com.reflecty.configurations;

import com.reflecty.matchers.ConstantTypeContainer;
import com.reflecty.testModels.TestClass1;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.*;

public class ConstantModuleTest {

    private ConstantModule constantModule;

    @Before
    public void setUp() throws Exception {
        constantModule = new ConstantModule();
    }

    @Test
    public void findMatch_returnsTheMatchingConstant() throws Exception {
        TestClass1 expectedConstant = new TestClass1();

        ConstantTypeContainer<TestClass1> tConstantTypeMatcher = new ConstantTypeContainer<>(expectedConstant, TestClass1.class);

        constantModule.register(TestClass1.class, tConstantTypeMatcher);

        TestClass1 actualConstant = constantModule.findMatch(TestClass1.class);

        assertThat(actualConstant, sameInstance(expectedConstant));
    }
}