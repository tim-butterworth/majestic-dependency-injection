package com.reflecty.configurations;

import com.reflecty.testModels.TestClass1;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MatchingContainerImplTest {
    @Test
    public void matches_returnsFalseForAClassThatDoesNotMatch() throws Exception {
        MatchingContainerImpl<TestClass1> constantTypeContainer = new MatchingContainerImpl<>(new TestClass1(), TestClass1.class);

        assertThat(constantTypeContainer.matches(new DecoratedClass<>(String.class)), is(false));
    }

    @Test
    public void matches_returnsTrueForAClassThatDoesMatch() throws Exception {
        MatchingContainerImpl<TestClass1> constantTypeContainer = new MatchingContainerImpl<>(new TestClass1(), TestClass1.class);

        assertThat(constantTypeContainer.matches(new DecoratedClass<>(TestClass1.class)), is(true));
    }

}