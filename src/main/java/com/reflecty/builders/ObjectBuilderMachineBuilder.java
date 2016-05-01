package com.reflecty.builders;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.cachingStrategies.NoCachingStrategy;
import com.reflecty.cachingStrategies.SingletonCacheStrategy;
import com.reflecty.configurations.BuildModule;
import com.reflecty.configurations.TypeMatcher;
import com.reflecty.creators.InstanceCreatorMachine;
import com.reflecty.matchers.EverythingMatcher;
import com.reflecty.matchers.SingletonMatcherImpl;
import com.reflecty.helperObjects.ObjectContainer;
import com.reflecty.instantiators.ReflectiveInstantiator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ObjectBuilderMachineBuilder {

    private BuildModule module;
    private List<InterfaceMatcherPair> interfaceMatcherPairList = new LinkedList<>();

    public ObjectBuilderMachine build() {
        ObjectContainer<ObjectBuilderMachine> objectBuilderMachineObjectContainer = new ObjectContainer<>();

        if (module == null) module = new BuildModule();
        interfaceMatcherPairList.forEach(item -> module.register(item.getAnImplementation(), item.getTypeMatcher()));

        ReflectiveInstantiator reflectiveInstantiator = new ReflectiveInstantiator(objectBuilderMachineObjectContainer, module);

        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachine(
                Collections.singletonList(getSingletonInstanceCreatorMachine(reflectiveInstantiator)),
                getDefaultInstanceCreatorMachine(reflectiveInstantiator)
        );

        objectBuilderMachineObjectContainer.addToContainer(objectBuilderMachine);

        return objectBuilderMachine;
    }

    public <T, R extends T> ObjectBuilderMachineBuilder register(Class<R> anImplementation, TypeMatcher<T> typeMatcher) {
        interfaceMatcherPairList.add(new InterfaceMatcherPair<>(anImplementation, typeMatcher));
        return this;
    }

    private InstanceCreatorMachine getSingletonInstanceCreatorMachine(ReflectiveInstantiator reflectiveInstantiator) {
        return new InstanceCreatorMachine(reflectiveInstantiator, new SingletonCacheStrategy(), new SingletonMatcherImpl());
    }

    private InstanceCreatorMachine getDefaultInstanceCreatorMachine(ReflectiveInstantiator reflectiveInstantiator) {
        return new InstanceCreatorMachine(reflectiveInstantiator, new NoCachingStrategy(), new EverythingMatcher());
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
