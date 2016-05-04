package com.reflecty.configurations;

import com.reflecty.testModels.TestClass1;
import org.junit.Test;
import org.junit.runners.model.TestClass;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ConstantTypeContainerImplTest {
    @Test
    public void matches_returnsFalseForAClassThatDoesNotMatch() throws Exception {
        ConstantTypeContainerImpl<TestClass1> constantTypeContainer = new ConstantTypeContainerImpl<>(new TestClass1(), TestClass1.class);

        assertThat(constantTypeContainer.matches(new DecoratedClass<>(String.class)), is(false));
    }

    @Test
    public void matches_returnsTrueForAClassThatDoesMatch() throws Exception {
        ConstantTypeContainerImpl<TestClass1> constantTypeContainer = new ConstantTypeContainerImpl<>(new TestClass1(), TestClass1.class);

        assertThat(constantTypeContainer.matches(new DecoratedClass<>(TestClass1.class)), is(true));
    }

}