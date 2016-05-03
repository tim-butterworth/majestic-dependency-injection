package com.reflecty.builders;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.annotations.Singleton;
import com.reflecty.cachingStrategies.NoCachingStrategy;
import com.reflecty.cachingStrategies.SingletonCacheStrategy;
import com.reflecty.configurations.ConstantModule;
import com.reflecty.configurations.InterfaceModule;
import com.reflecty.configurations.DecoratedClass;
import com.reflecty.configurations.TypeMatcher;
import com.reflecty.creators.InstanceCreatorMachine;
import com.reflecty.helperObjects.ObjectContainer;
import com.reflecty.instantiators.ReflectiveInstantiator;
import com.reflecty.matchers.ConstantTypeContainer;
import com.reflecty.matchers.ObjectMatcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ObjectBuilderMachineBuilder {

    private InterfaceModule module;
    private ConstantModule constantModule;
    private List<InterfaceMatcherPair> interfaceMatcherPairList = new LinkedList<>();

    public ObjectBuilderMachine build() {
        ObjectContainer<ObjectBuilderMachine> objectBuilderMachineObjectContainer = new ObjectContainer<>();

        if (module == null) module = new InterfaceModule();
        interfaceMatcherPairList.forEach(item -> module.register(item.getAnImplementation(), item.getTypeMatcher()));

        InstanceCreatorMachine instanceCreatorMachine = new InstanceCreatorMachine();

        Function<DecoratedClass<?>, Boolean> matchAllForInstantiator = o -> true;

        Function<Class<?>, Boolean> matchAlForCaching = o -> true;
        Function<Class<?>, Boolean> matchSingletonCaching = aClass -> !Arrays.asList(aClass.getDeclaredAnnotations())
                .stream()
                .filter(annotation -> annotation instanceof Singleton)
                .collect(Collectors.toList()).isEmpty();

        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachine(
                Collections.singletonList(new ObjectMatcher<>(matchAllForInstantiator, new ReflectiveInstantiator(objectBuilderMachineObjectContainer))),
                Arrays.asList(
                        new ObjectMatcher<>(matchSingletonCaching, new SingletonCacheStrategy()),
                        new ObjectMatcher<>(matchAlForCaching, new NoCachingStrategy())
                ),
                instanceCreatorMachine,
                module
        );

        objectBuilderMachineObjectContainer.addToContainer(objectBuilderMachine);

        return objectBuilderMachine;
    }

    public <T, R extends T> ObjectBuilderMachineBuilder registerImplmentation(Class<R> anImplementation, TypeMatcher<T> typeMatcher) {
        interfaceMatcherPairList.add(new InterfaceMatcherPair<>(anImplementation, typeMatcher));
        return this;
    }

    public <T> ObjectBuilderMachineBuilder registerConstant(String key, ConstantTypeContainer<T> one) {

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

        TypeMatcher<T> getTypeMatcher() {
            return typeMatcher;
        }
    }
}
