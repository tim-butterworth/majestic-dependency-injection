package com.reflecty.creators;

import com.reflecty.instantiators.ReflectiveInstantiator;
import com.reflecty.testModels.SpecialRunnable;
import com.reflecty.testModels.TestClass1;
import com.reflecty.testModels.TestClass2;
import com.reflecty.testModels.TestClass3;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class SingletonInstanceCreatorTest {

    private SingletonInstanceCreator singletonInstanceCreator;

    @Before
    public void setUp() throws Exception {
        singletonInstanceCreator = new SingletonInstanceCreator(mock(ReflectiveInstantiator.class));
    }

    @Test
    public void getInstance_differentTypesOfObjectsShouldBeAddedInParallel() throws Exception {
        List<Thread> threads = Arrays.asList(
                new Thread(new SpecialRunnable<>(getFunctionForClass(), Object.class)),
                new Thread(new SpecialRunnable<>(getFunctionForClass(), TestClass1.class)),
                new Thread(new SpecialRunnable<>(getFunctionForClass(), TestClass2.class)),
                new Thread(new SpecialRunnable<>(getFunctionForClass(), TestClass3.class))
        );

        assertProcessTime(threads, duration -> duration < 4000);
    }

    @Test
    public void getInstance_sameTypesOfObjectsShouldBeAddedInSeries() throws Exception {
        SpecialRunnable<Object> runnable = new SpecialRunnable<>(getFunctionForClass(), Object.class);

        List<Thread> threads = Arrays.asList(
                new Thread(runnable),
                new Thread(runnable),
                new Thread(runnable),
                new Thread(runnable)
        );

        assertProcessTime(threads, duration -> duration > 4000);
    }

    private void assertProcessTime(List<Thread> threads, Function<Long, Boolean> function) throws InterruptedException {
        Long start = new Date().getTime();

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        Long end = new Date().getTime();

        assertThat(function.apply(end-start), is(true));
    }

    private <T> Function<Class<T>, T> getFunctionForClass() {
        return objectClass -> singletonInstanceCreator.getInstance(objectClass);
    }

}