package com.reflecty.integration;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.testModels.ConstructorTonClass;
import com.reflecty.testModels.NonSingleTonClass;
import com.reflecty.testModels.SingleTonClass;
import com.reflecty.testModels.SpecialRunnable;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

public class ObjectBuilderMachineIntegrationTest {

    private ObjectBuilderMachine objectBuilderMachine;

    @Before
    public void setUp() throws Exception {
        objectBuilderMachine = new ObjectBuilderMachine();
    }

    @Test
    public void name() throws Exception {
        NonSingleTonClass nonSingleTonClass = objectBuilderMachine.getInstance(NonSingleTonClass.class);

        assertThat(nonSingleTonClass, not(nullValue()));
    }

    @Test
    public void singletony_whenAnnotatedWithSingleton() throws Exception {
        SingleTonClass singleTonClass = objectBuilderMachine.getInstance(SingleTonClass.class);
        SingleTonClass singleTonClass2 = objectBuilderMachine.getInstance(SingleTonClass.class);

        assertThat(singleTonClass, not(nullValue()));
        assertThat(singleTonClass2, not(nullValue()));
        assertThat(singleTonClass, sameInstance(singleTonClass2));
    }

    @Test
    public void not_singletony_whenNotAnnotatedWithSingleton() throws Exception {
        NonSingleTonClass nonSingleTonClass = objectBuilderMachine.getInstance(NonSingleTonClass.class);
        NonSingleTonClass nonSingleTonClass2 = objectBuilderMachine.getInstance(NonSingleTonClass.class);

        assertThat(nonSingleTonClass, not(nullValue()));
        assertThat(nonSingleTonClass2, not(nullValue()));
        assertThat(nonSingleTonClass, not(sameInstance(nonSingleTonClass2)));
    }

    @Test
    public void not_singletony_whenNotAnnotatedWithSingleton_isThreadSafeProbably() throws Exception {
        SpecialRunnable<SingleTonClass> runnable1 = new SpecialRunnable<>(objectBuilderMachine, SingleTonClass.class);
        SpecialRunnable<SingleTonClass> runnable2 = new SpecialRunnable<>(objectBuilderMachine, SingleTonClass.class);
        SpecialRunnable<SingleTonClass> runnable3 = new SpecialRunnable<>(objectBuilderMachine, SingleTonClass.class);

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
    public void createClassWithANonEmptyContstructor() throws Exception {
        ConstructorTonClass constructorTonClass = objectBuilderMachine.getInstance(ConstructorTonClass.class);

        assertThat(constructorTonClass, not(nullValue()));
        assertThat(constructorTonClass.getSingleTonClass(), not(nullValue()));
    }

    @Test
    public void createClassWithANonEmptyContstructor_enforcesSingletonNess() throws Exception {
        ConstructorTonClass constructorTonClass1 = objectBuilderMachine.getInstance(ConstructorTonClass.class);
        ConstructorTonClass constructorTonClass2 = objectBuilderMachine.getInstance(ConstructorTonClass.class);

        assertThat(constructorTonClass1.getSingleTonClass(), not(nullValue()));
        assertThat(constructorTonClass2.getSingleTonClass(), not(nullValue()));

        assertThat(constructorTonClass1.getSingleTonClass(), sameInstance(constructorTonClass2.getSingleTonClass()));
    }
}
