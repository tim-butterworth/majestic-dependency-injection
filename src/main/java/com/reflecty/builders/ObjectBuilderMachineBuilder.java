package com.reflecty.builders;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.configurations.BuildModule;
import com.reflecty.configurations.TypeMatcher;
import com.reflecty.factories.InstanceCreatorFactory;
import com.reflecty.instantiators.ReflectiveInstantiator;
import com.reflecty.helperObjects.ObjectContainer;

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
        InstanceCreatorFactory instanceCreatorFactory = new InstanceCreatorFactory(reflectiveInstantiator);
        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachine(instanceCreatorFactory);

        objectBuilderMachineObjectContainer.addToContainer(objectBuilderMachine);

        return objectBuilderMachine;
    }

    public <T, R extends T> ObjectBuilderMachineBuilder register(Class<R> anImplementation, TypeMatcher<T> typeMatcher) {
        interfaceMatcherPairList.add(new InterfaceMatcherPair<>(anImplementation, typeMatcher));
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
