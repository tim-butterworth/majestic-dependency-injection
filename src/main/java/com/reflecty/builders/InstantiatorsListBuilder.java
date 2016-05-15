package com.reflecty.builders;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.annotations.Constant;
import com.reflecty.annotations.Proxied;
import com.reflecty.configurations.ConstantModule;
import com.reflecty.configurations.DecoratedClass;
import com.reflecty.helperObjects.ObjectContainer;
import com.reflecty.instantiators.*;
import com.reflecty.matchers.ObjectMatcher;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InstantiatorsListBuilder {
    public List<ObjectMatcher<DecoratedClass<?>, Instantiator>> getInstantiatorsList(ObjectContainer<ObjectBuilderMachine> objectBuilderMachineObjectContainer, ConstantModule constantModule) {
        Function<DecoratedClass<?>, Boolean> matchAllForInstantiator = o -> true;

        Function<DecoratedClass<?>, Boolean> matchConstantInstantiator = decoratedClass -> !Arrays.asList(decoratedClass.getExtraAnnotations())
                .stream()
                .filter(annotation -> annotation instanceof Constant)
                .collect(Collectors.toList())
                .isEmpty();

        Function<DecoratedClass<?>, Boolean> matchProxiedClassInstantiator = decoratedClass -> Arrays.asList(decoratedClass.getContainedClass().getDeclaredAnnotations())
                .stream()
                .filter(annotation -> annotation instanceof Proxied)
                .findFirst()
                .map(annotation -> true)
                .orElse(false);

        Function<DecoratedClass<?>, Boolean> matchProxieInterfaceInstantiator = decoratedClass -> decoratedClass.getContainedClass().isInterface();

        CreateObjectFromConstructor createObjectFromConstructor = new CreateObjectFromConstructor(objectBuilderMachineObjectContainer);
        return Arrays.asList(
                new ObjectMatcher<>(matchProxieInterfaceInstantiator, new ProxiedInterfaceInstantiator(objectBuilderMachineObjectContainer)),
                new ObjectMatcher<>(matchProxiedClassInstantiator, new ProxiedClassInstantiator(createObjectFromConstructor)),
                new ObjectMatcher<>(matchConstantInstantiator, new ConstantInstantiator(constantModule)),
                new ObjectMatcher<>(matchAllForInstantiator, new ReflectiveInstantiator(createObjectFromConstructor))
        );
    }
}