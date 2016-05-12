package com.reflecty.integration;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.builders.ObjectBuilderMachineBuilder;
import com.reflecty.configurations.*;
import com.reflecty.testModels.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
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
                .registerImplmentation(new NamespaceTypeMatcherImpl<>(
                        "One",
                        InterfaceForAnObject.class
                ), ImplOne.class
                )
                .registerImplmentation(new NamespaceTypeMatcherImpl<>(
                        "Two",
                        InterfaceForAnObject.class
                ), ImplTwo.class
                )
                .build();

        ConstructorWithAnnotatedParams instance = objectBuilderMachine.getInstance(ConstructorWithAnnotatedParams.class);

        assertThat(instance.getFirstObj().getClassName(), is("ImplOne"));
        assertThat(instance.getSecondObj().getClassName(), is("ImplTwo"));
    }

    @Test
    public void createClassUsingAModule_withConstants() throws Exception {
        MatchingContainer<String> one = new MatchingContainerImpl<>(
                "One constant",
                String.class
        );

        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachineBuilder().registerConstant(one).build();

        ConstantTonClass instance = objectBuilderMachine.getInstance(ConstantTonClass.class);

        assertThat(instance.getString(), is("One constant"));
    }

    @Test
    public void createClassUsingAModule_withNamespacedConstants() throws Exception {
        NamespacedMatchingContainerImpl<String> one = new NamespacedMatchingContainerImpl<>(
                "value.that.is.constant.one",
                "One constant",
                String.class
        );

        NamespacedMatchingContainerImpl<Long> two = new NamespacedMatchingContainerImpl<>(
                "value.that.is.constant.two",
                31415L,
                Long.class
        );

        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachineBuilder()
                .registerConstant(one)
                .registerConstant(two)
                .build();

        NamespaceConstantTonClass instance = objectBuilderMachine.getInstance(NamespaceConstantTonClass.class);

        assertThat(instance.getOne(), is("One constant"));
        assertThat(instance.getTwo(), is(31415L));
    }

    @Test
    public void createAProxiedClass_onlyStringValue() throws Exception {
        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachineBuilder().build();

        RandomStringInterface instance = objectBuilderMachine.getInstance(RandomStringInterface.class);
        RandomStringInterface instance2 = objectBuilderMachine.getInstance(RandomStringInterface.class);

        String aString1 = instance.getAString();
        String aString2 = instance2.getAString();

        assertThat(aString1, not(nullValue()));
        assertThat(aString2, not(nullValue()));
        assertThat(aString1, not(equalTo(aString2)));
    }

    @Test
    public void createASingletonProxiedClass_onlyStringValue() throws Exception {
        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachineBuilder().build();

        RandomSingletonStringInterface instance = objectBuilderMachine.getInstance(RandomSingletonStringInterface.class);
        RandomSingletonStringInterface instance1 = objectBuilderMachine.getInstance(RandomSingletonStringInterface.class);

        String aString1 = instance.getSomeValue();
        String aString2 = instance1.getSomeValue();

        assertThat(aString1, not(nullValue()));
        assertThat(aString2, not(nullValue()));
        assertThat(aString1, equalTo(aString2));
    }

    @Test
    public void createALazyChainOfClasses() throws Exception {
        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachineBuilder().build();

        NodeChain current = objectBuilderMachine.getInstance(NodeChain.class);
        int i = 0;
        while (i < 10) {
            NodeChain previous = current;
            NodeChain next = current.next();
            current = next;

            assertThat(next, not(nullValue()));
            assertThat(previous, not(sameInstance(next)));
            i++;
        }
    }

    @Test
    public void createAProviderManually() throws Exception {
        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachineBuilder()
                .registerImplmentation(
                        new NamespaceTypeMatcherImpl<>("OracleFactory", FancyFactory.class), OracleFancyFactoryImpl.class
                ).build();

        CustomInstanceProvider instance = objectBuilderMachine.getInstance(CustomInstanceProvider.class);

        FancyFactory fancyFactory = instance.getInstance().getFancyFactory();

        assertThat(fancyFactory, instanceOf(OracleFancyFactoryImpl.class));
    }

}
