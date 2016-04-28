package com.reflecty.factories;

import com.reflecty.chainclasses.MatcherForInstance;
import com.reflecty.chainclasses.MatcherForSingletonInstanceCreator;
import com.reflecty.creators.DefaultInstanceCreator;
import com.reflecty.creators.InstanceCreator;
import com.reflecty.instantiators.ReflectiveInstantiator;

import java.util.ArrayList;
import java.util.List;

public class InstanceCreatorFactory {

    private final List<MatcherForInstance<? extends InstanceCreator, Class<?>>> matcherForInstanceList;
    private final DefaultInstanceCreator defaultInstanceCreator;

    public InstanceCreatorFactory(ReflectiveInstantiator reflectiveInstantiator) {
        matcherForInstanceList = new ArrayList<>();
        defaultInstanceCreator = new DefaultInstanceCreator(reflectiveInstantiator);
        matcherForInstanceList.add(new MatcherForSingletonInstanceCreator(reflectiveInstantiator));
    }

    public <T> InstanceCreator getInstanceCreator(Class<T> tClass) {
        for (MatcherForInstance<? extends InstanceCreator, Class<?>> matcherForInstance : matcherForInstanceList) {
            if (matcherForInstance.matches(tClass)) {
                return matcherForInstance.getInstance();
            }
        }
        return defaultInstanceCreator;
    }

}
