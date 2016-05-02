package com.reflecty.cachingStrategies;

import com.reflecty.configurations.DecoratedClass;
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
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class SingletonCacheStrategyTest {
    private final ReflectiveInstantiator mock = mock(ReflectiveInstantiator.class);
    private SingletonCacheStrategy singletonInstanceCreator;

    @Before
    public void setUp() throws Exception {
        singletonInstanceCreator = new SingletonCacheStrategy();
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

    @Test
    public void getInstance_callsGetInstanceWhenInstanceIsNotChached() throws Exception {
        DecoratedClass<TestClass1> classContainer = new DecoratedClass<>(TestClass1.class);

        singletonInstanceCreator.getInstance(classContainer, mock);

        verify(mock).instantiate(classContainer);
        verifyNoMoreInteractions(mock);
    }

    @Test
    public void getInstance_doesNotCallgetInstanceWhenInstanceAlreadyCached() throws Exception {
        DecoratedClass<TestClass1> classContainer = new DecoratedClass<>(TestClass1.class);

        TestClass1 testClass1 = new TestClass1();

        when(mock.instantiate(classContainer)).thenReturn(testClass1);

        TestClass1 instance = singletonInstanceCreator.getInstance(classContainer, mock);
        TestClass1 instance2 = singletonInstanceCreator.getInstance(classContainer, mock);

        assertThat(instance, sameInstance(instance2));

        verify(mock).instantiate(classContainer);
        verifyNoMoreInteractions(mock);
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
        return objectClass -> singletonInstanceCreator.getInstance(new DecoratedClass<>(objectClass), mock);
    }

}