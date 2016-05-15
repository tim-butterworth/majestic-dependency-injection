package com.reflecty.builders;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.configurations.ConstantModule;
import com.reflecty.configurations.InterfaceModule;
import com.reflecty.configurations.TypeMatcher;
import com.reflecty.creators.InstanceCreatorMachine;
import com.reflecty.helperObjects.ObjectContainer;
import com.reflecty.configurations.MatchingContainer;

import java.util.LinkedList;
import java.util.List;

public class ObjectBuilderMachineBuilder {

    private final InstantiatorsListBuilder instantiatorsListBuilder = new InstantiatorsListBuilder();
    private final CachingStrategyListBuilder cachingStrategyListBuilder = new CachingStrategyListBuilder();
    private InterfaceModule interfaceModule;
    private ConstantModule constantModule;
    private List<InterfaceMatcherPair> interfaceMatcherPairList = new LinkedList<>();
    private List<ConstantMatcherPair> constantMatcherPairList = new LinkedList<>();

    public ObjectBuilderMachine build() {
        ObjectContainer<ObjectBuilderMachine> objectBuilderMachineObjectContainer = new ObjectContainer<>();

        populateInterfaceModule();
        populateConstantModule();

        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachine(
                instantiatorsListBuilder.getInstantiatorsList(objectBuilderMachineObjectContainer, constantModule),
                cachingStrategyListBuilder.getCachingStrategiesList(),
                new InstanceCreatorMachine(),
                interfaceModule
        );
        objectBuilderMachineObjectContainer.addToContainer(objectBuilderMachine);

        return objectBuilderMachine;
    }

    public <T, R extends T> ObjectBuilderMachineBuilder registerImplmentation(TypeMatcher<T> typeMatcher, Class<R> anImplementation) {
        interfaceMatcherPairList.add(new InterfaceMatcherPair<>(anImplementation, typeMatcher));
        return this;
    }

    public <T> ObjectBuilderMachineBuilder registerConstant(MatchingContainer<T> one) {
        constantMatcherPairList.add(new ConstantMatcherPair<>(one));
        return this;
    }

    private void populateConstantModule() {
        constantModule = new ConstantModule();
        constantMatcherPairList.forEach(item -> constantModule.register(item.getTypeMatcher()));
    }

    private void populateInterfaceModule() {
        interfaceModule = new InterfaceModule();
        interfaceMatcherPairList.forEach(item -> interfaceModule.register(item.getTypeMatcher(), item.getAnImplementation()));
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
        private final MatchingContainer<T> one;

        public ConstantMatcherPair(MatchingContainer<T> one) {
            this.one = one;
        }

        public MatchingContainer<T> getTypeMatcher() {
            return one;
        }
    }
}
