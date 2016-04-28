package com.reflecty.integration;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.ObjectBuilderMachineBuilder;
import com.reflecty.testModels.ConstructorTonClass;
import com.reflecty.testModels.NonSingleTonClass;
import com.reflecty.testModels.SingleTonClass;
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
        objectBuilderMachine = ObjectBuilderMachineBuilder.build();
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
