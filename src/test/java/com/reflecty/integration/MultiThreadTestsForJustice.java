package com.reflecty.integration;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.builders.ObjectBuilderMachineBuilder;
import com.reflecty.configurations.NamespaceTypeMatcherImpl;
import com.reflecty.testModels.*;
import org.junit.Test;

import java.util.function.Function;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

public class MultiThreadTestsForJustice {

    private ObjectBuilderMachine objectBuilderMachine;

    @Test
    public void not_singletony_whenNotAnnotatedWithSingleton_isThreadSafeProbably() throws Exception {
        objectBuilderMachine = new ObjectBuilderMachineBuilder().build();

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

    @Test
    public void not_singletony_whenNotAnnotatedWithSingleton_isThreadSafeProbably_evenForRegisteredClasses() throws Exception {
        objectBuilderMachine = new ObjectBuilderMachineBuilder()
                .registerImplmentation(
                        new NamespaceTypeMatcherImpl<>("One", InterfaceForAnObject.class), ImplOne.class
                ).registerImplmentation(
                        new NamespaceTypeMatcherImpl<>("Two", InterfaceForAnObject.class), ImplTwo.class
                ).build();

        Function<Class<ConstructorWithAnnotatedParams>, ConstructorWithAnnotatedParams> function = clazz -> objectBuilderMachine.getInstance(clazz);

        SpecialRunnable<ConstructorWithAnnotatedParams> runnable1 = new SpecialRunnable<>(function, ConstructorWithAnnotatedParams.class);
        SpecialRunnable<ConstructorWithAnnotatedParams> runnable2 = new SpecialRunnable<>(function, ConstructorWithAnnotatedParams.class);
        SpecialRunnable<ConstructorWithAnnotatedParams> runnable3 = new SpecialRunnable<>(function, ConstructorWithAnnotatedParams.class);

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

        ConstructorWithAnnotatedParams instance = runnable1.getInstance();
        ConstructorWithAnnotatedParams instance1 = runnable2.getInstance();
        ConstructorWithAnnotatedParams instance2 = runnable3.getInstance();

        assertThat(instance, not(nullValue()));
        assertThat(instance, not(sameInstance(instance1)));

        assertThat(instance.getSecondObj(), sameInstance(instance1.getSecondObj()));
        assertThat(instance1.getSecondObj(), sameInstance(instance2.getSecondObj()));
    }
}
