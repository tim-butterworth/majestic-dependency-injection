package com.reflecty.matchers;

import com.reflecty.testModels.NonSingleTonClass;
import com.reflecty.testModels.SingleTonClass;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SingletonMatcherImplTest {

    private SingletonMatcherImpl singletonMatcher;

    @Before
    public void setUp() throws Exception {
        singletonMatcher = new SingletonMatcherImpl();
    }

    @Test
    public void canHandle_returnsTrueForSingletonClasses() throws Exception {
        assertThat(singletonMatcher.canHandle(SingleTonClass.class), is(true));
    }

    @Test
    public void canHandle_returnsFalseForNonSingletonClasses() throws Exception {
        assertThat(singletonMatcher.canHandle(NonSingleTonClass.class), is(false));
    }

}