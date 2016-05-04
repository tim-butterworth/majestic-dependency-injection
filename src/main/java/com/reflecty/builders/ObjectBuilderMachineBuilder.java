package com.reflecty.builders;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.annotations.Constant;
import com.reflecty.annotations.Singleton;
import com.reflecty.cachingStrategies.CachingStrategy;
import com.reflecty.cachingStrategies.NoCachingStrategy;
import com.reflecty.cachingStrategies.SingletonCacheStrategy;
import com.reflecty.configurations.ConstantModule;
import com.reflecty.configurations.InterfaceModule;
import com.reflecty.configurations.DecoratedClass;
import com.reflecty.configurations.TypeMatcher;
import com.reflecty.creators.InstanceCreatorMachine;
import com.reflecty.helperObjects.ObjectContainer;
import com.reflecty.instantiators.ConstantInstantiator;
import com.reflecty.instantiators.Instantiator;
import com.reflecty.instantiators.ReflectiveInstantiator;
import com.reflecty.configurations.ConstantTypeContainer;
import com.reflecty.matchers.ObjectMatcher;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ObjectBuilderMachineBuilder {

    private InterfaceModule interfaceModule;
    private ConstantModule constantModule;
    private List<InterfaceMatcherPair> interfaceMatcherPairList = new LinkedList<>();
    private List<ConstantMatcherPair> constantMatcherPairList = new LinkedList<>();

    public ObjectBuilderMachine build() {
        ObjectContainer<ObjectBuilderMachine> objectBuilderMachineObjectContainer = new ObjectContainer<>();

        populateInterfaceModule();
        populateConstantModule();

        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachine(
                getInstantiatorsList(objectBuilderMachineObjectContainer),
                getCachingStrategiesList(),
                new InstanceCreatorMachine(),
                interfaceModule
        );

        objectBuilderMachineObjectContainer.addToContainer(objectBuilderMachine);

        return objectBuilderMachine;
    }

    private void populateConstantModule() {
        constantModule = new ConstantModule();
        constantMatcherPairList.forEach(item -> constantModule.register(item.getTypeMatcher()));
    }

    private void populateInterfaceModule() {
        interfaceModule = new InterfaceModule();
        interfaceMatcherPairList.forEach(item -> interfaceModule.register(item.getAnImplementation(), item.getTypeMatcher()));
    }

    private List<ObjectMatcher<Class<?>, CachingStrategy>> getCachingStrategiesList() {
        Function<Class<?>, Boolean> matchAllForCaching = o -> true;
        Function<Class<?>, Boolean> matchSingletonCaching = aClass -> !Arrays.asList(aClass.getDeclaredAnnotations())
                .stream()
                .filter(annotation -> annotation instanceof Singleton)
                .collect(Collectors.toList()).isEmpty();

        return Arrays.asList(
                new ObjectMatcher<>(matchSingletonCaching, new SingletonCacheStrategy()),
                new ObjectMatcher<>(matchAllForCaching, new NoCachingStrategy())
        );
    }

    private List<ObjectMatcher<DecoratedClass<?>, Instantiator>> getInstantiatorsList(ObjectContainer<ObjectBuilderMachine> objectBuilderMachineObjectContainer) {
        Function<DecoratedClass<?>, Boolean> matchAllForInstantiator = o -> true;
        Function<DecoratedClass<?>, Boolean> matchConstantInstantiator = decoratedClass -> !Arrays.asList(decoratedClass.getExtraAnnotations())
                .stream()
                .filter(annotation -> annotation instanceof Constant)
                .collect(Collectors.toList())
                .isEmpty();

        return Arrays.asList(
                new ObjectMatcher<>(matchConstantInstantiator, new ConstantInstantiator(constantModule)),
                new ObjectMatcher<>(matchAllForInstantiator, new ReflectiveInstantiator(objectBuilderMachineObjectContainer))
        );
    }

    public <T, R extends T> ObjectBuilderMachineBuilder registerImplmentation(Class<R> anImplementation, TypeMatcher<T> typeMatcher) {
        interfaceMatcherPairList.add(new InterfaceMatcherPair<>(anImplementation, typeMatcher));
        return this;
    }

    public <T> ObjectBuilderMachineBuilder registerConstant(Class<T> key, ConstantTypeContainer<T> one) {
        constantMatcherPairList.add(new ConstantMatcherPair<>(key, one));
        return this;
    }

    private class InterfaceMatcherPair<M extends T, T> {
        private final Class<M> anImplementation;
        private final TypeMatcher<T> typeMatcher;

        InterfaceMatcherPair(Class<M> anImplementation, TypeMatcher<T> typeMatcher) {
            this.anImplementation = anImplementation;
            this.typeMatcher = typeMatcher;
        }

        Class<M> getAnImplementation() {
            return anImplementation;
        }

        TypeMatcher<? extends T> getTypeMatcher() {
            return typeMatcher;
        }
    }

    private class ConstantMatcherPair<T> {
        private final Class<T> key;
        private final ConstantTypeContainer<T> one;

        public ConstantMatcherPair(Class<T> key, ConstantTypeContainer<T> one) {
            this.key = key;
            this.one = one;
        }

        public Class<T> getAnImplementation() {
            return key;
        }

        public ConstantTypeContainer<T> getTypeMatcher() {
            return one;
        }
    }
}
