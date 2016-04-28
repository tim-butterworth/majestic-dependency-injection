package com.reflecty.integration;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.ObjectBuilderMachineBuilder;
import com.reflecty.testModels.SingleTonClass;
import com.reflecty.testModels.SpecialRunnable;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

public class MultiThreadTestsForJustice {

    private ObjectBuilderMachine objectBuilderMachine;

    @Before
    public void setUp() throws Exception {
        objectBuilderMachine = ObjectBuilderMachineBuilder.build();
    }

    @Test
    public void not_singletony_whenNotAnnotatedWithSingleton_isThreadSafeProbably() throws Exception {
        Function<Class<SingleTonClass>, SingleTonClass> function = clazz -> objectBuilderMachine.getInstance(clazz);
        SpecialRunnable<SingleTonClass> runnable1 = new SpecialRunnable<>(function, SingleTonClass.class);
        SpecialRunnable<SingleTonClass> runnable2 = new SpecialRunnable<>(function, SingleTonClass.class);
        SpecialRunnable<SingleTonClass> runnable3 = new SpecialRunnable<>(function, SingleTonClass.class);

        Thread t1 = new Thread(runnable1);
        t1.setName("t1 thread of justice!!");

        Thread t2 = new Thread(runnable2);
        t2.setName("t2 a roguish thread!!");

        Thread t3 = new Thread(runnable3);
        t3.setName("t3 a helpless, weak thread!!");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        SingleTonClass instance = runnable1.getInstance();
        SingleTonClass instance1 = runnable2.getInstance();

        assertThat(instance, not(nullValue()));
        assertThat(instance, sameInstance(instance1));
    }

}
