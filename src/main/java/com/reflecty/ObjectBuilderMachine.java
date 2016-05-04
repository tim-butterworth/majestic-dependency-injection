package com.reflecty;

import com.reflecty.cachingStrategies.CachingStrategy;
import com.reflecty.configurations.InterfaceModule;
import com.reflecty.configurations.DecoratedClass;
import com.reflecty.creators.InstanceCreatorMachine;
import com.reflecty.instantiators.Instantiator;
import com.reflecty.matchers.ObjectMatcher;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectBuilderMachine {
    private List<ObjectMatcher<DecoratedClass<?>, Instantiator>> instantatorMatcher;
    private List<ObjectMatcher<Class<?>, CachingStrategy>> cachingStrategyMatcher;
    private InstanceCreatorMachine instanceCreatorMachine;
    private InterfaceModule interfaceModule;

    public ObjectBuilderMachine(
            List<ObjectMatcher<DecoratedClass<?>, Instantiator>> instantatorMatcher,
            List<ObjectMatcher<Class<?>, CachingStrategy>> cachingStrategyMatcher,
            InstanceCreatorMachine instanceCreatorMachine,
            InterfaceModule interfaceModule
    ) {
        this.instantatorMatcher = instantatorMatcher;
        this.cachingStrategyMatcher = cachingStrategyMatcher;
        this.instanceCreatorMachine = instanceCreatorMachine;
        this.interfaceModule = interfaceModule;
    }

    public <T> T getInstance(Class<T> clazz, Annotation... extraAnnotations) {
        DecoratedClass<T> decoratedClass = new DecoratedClass<>(clazz, extraAnnotations);
        Class<T> match = interfaceModule.getMatch(decoratedClass);

        return instanceCreatorMachine.getInstance(
                new DecoratedClass<>(match, extraAnnotations),
                getCachingStrategy(match),
                getInstantiator(match, extraAnnotations)
        );
    }

    private <T> CachingStrategy getCachingStrategy(Class<T> match) {
        return getMatchFromMatcherList(match, cachingStrategyMatcher);
    }

    private <T> Instantiator getInstantiator(Class<T> match, Annotation[] extraAnnotations) {
        return getMatchFromMatcherList(new DecoratedClass<>(match, extraAnnotations), instantatorMatcher);
    }

    private <T, K> T getMatchFromMatcherList(K key, List<ObjectMatcher<K, T>> matchOptions) {
        return matchOptions.stream()
                .filter(matcher -> matcher.matches(key))
                .collect(Collectors.toList())
                .get(0)
                .getObject();
    }
}
