package com.reflecty.integration;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.builders.ObjectBuilderMachineBuilder;
import com.reflecty.configurations.ConstantTypeContainerImpl;
import com.reflecty.configurations.NamespaceConstantTypeContainer;
import com.reflecty.configurations.NamespaceTypeMatcherImpl;
import com.reflecty.configurations.ConstantTypeContainer;
import com.reflecty.testModels.*;
import com.sun.xml.internal.fastinfoset.stax.events.EmptyIterator;
import org.junit.Test;

import static com.sun.xml.internal.fastinfoset.stax.events.EmptyIterator.instance;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

public class ObjectBuilderMachineIntegrationTest {

    @Test
    public void name() throws Exception {
        NonSingleTonClass nonSingleTonClass = new ObjectBuilderMachineBuilder().build().getInstance(NonSingleTonClass.class);

        assertThat(nonSingleTonClass, not(nullValue()));
    }

    @Test
    public void singletony_whenAnnotatedWithSingleton() throws Exception {
        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachineBuilder().build();

        SingleTonClass singleTonClass = objectBuilderMachine.getInstance(SingleTonClass.class);
        SingleTonClass singleTonClass2 = objectBuilderMachine.getInstance(SingleTonClass.class);

        assertThat(singleTonClass, not(nullValue()));
        assertThat(singleTonClass2, not(nullValue()));
        assertThat(singleTonClass, sameInstance(singleTonClass2));
    }

    @Test
    public void not_singletony_whenNotAnnotatedWithSingleton() throws Exception {
        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachineBuilder().build();

        NonSingleTonClass nonSingleTonClass = objectBuilderMachine.getInstance(NonSingleTonClass.class);
        NonSingleTonClass nonSingleTonClass2 = objectBuilderMachine.getInstance(NonSingleTonClass.class);

        assertThat(nonSingleTonClass, not(nullValue()));
        assertThat(nonSingleTonClass2, not(nullValue()));
        assertThat(nonSingleTonClass, not(sameInstance(nonSingleTonClass2)));
    }

    @Test
    public void createClassWithANonEmptyContstructor() throws Exception {
        ConstructorTonClass constructorTonClass = new ObjectBuilderMachineBuilder().build().getInstance(ConstructorTonClass.class);

        assertThat(constructorTonClass, not(nullValue()));
        assertThat(constructorTonClass.getSingleTonClass(), not(nullValue()));
    }

    @Test
    public void createClassWithANonEmptyContstructor_enforcesSingletonNess() throws Exception {
        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachineBuilder().build();

        ConstructorTonClass constructorTonClass1 = objectBuilderMachine.getInstance(ConstructorTonClass.class);
        ConstructorTonClass constructorTonClass2 = objectBuilderMachine.getInstance(ConstructorTonClass.class);

        assertThat(constructorTonClass1.getSingleTonClass(), not(nullValue()));
        assertThat(constructorTonClass2.getSingleTonClass(), not(nullValue()));

        assertThat(constructorTonClass1.getSingleTonClass(), sameInstance(constructorTonClass2.getSingleTonClass()));
    }

    @Test
    public void createClassUsingAModule() throws Exception {
        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachineBuilder()
                .registerImplmentation(ImplOne.class,
                        new NamespaceTypeMatcherImpl<>(
                                "One",
                                InterfaceForAnObject.class
                        )
                )
                .registerImplmentation(ImplTwo.class,
                        new NamespaceTypeMatcherImpl<>(
                                "Two",
                                InterfaceForAnObject.class
                        )
                )
                .build();

        ConstructorWithAnnotatedParams instance = objectBuilderMachine.getInstance(ConstructorWithAnnotatedParams.class);

        assertThat(instance.getFirstObj().getClassName(), is("ImplOne"));
        assertThat(instance.getSecondObj().getClassName(), is("ImplTwo"));
    }

    @Test
    public void createClassUsingAModule_withConstants() throws Exception {
        ConstantTypeContainer<String> one = new ConstantTypeContainerImpl<>(
                "One constant",
                String.class
        );

        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachineBuilder()
                .registerConstant(
                        String.class,
                        one
                ).build();

        ConstantTonClass instance = objectBuilderMachine.getInstance(ConstantTonClass.class);

        assertThat(instance.getString(), is("One constant"));
    }

    @Test
    public void createClassUsingAModule_withNamespacedConstants() throws Exception {
        NamespaceConstantTypeContainer<String> one = new NamespaceConstantTypeContainer<>(
                "value.that.is.constant.one",
                "One constant",
                String.class
        );

        NamespaceConstantTypeContainer<Long> two = new NamespaceConstantTypeContainer<>(
                "value.that.is.constant.two",
                31415L,
                Long.class
        );

        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachineBuilder()
                .registerConstant(String.class, one)
                .registerConstant(Long.class, two)
                .build();

        NamespaceConstantTonClass instance = objectBuilderMachine.getInstance(NamespaceConstantTonClass.class);

        assertThat(instance.getOne(), is("One constant"));
        assertThat(instance.getTwo(), is(31415L));
    }

}
