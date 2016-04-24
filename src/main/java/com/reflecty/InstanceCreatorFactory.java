package com.reflecty;


import com.reflecty.chainclasses.MatcherForInstance;
import com.reflecty.chainclasses.MatcherForSingletonInstanceCreator;
import com.reflecty.creators.DefaultInstanceCreator;
import com.reflecty.creators.InstanceCreator;

import java.util.ArrayList;
import java.util.List;

public class InstanceCreatorFactory {

    private final List<MatcherForInstance<? extends InstanceCreator, Class<?>>> matcherForInstanceList;
    private final DefaultInstanceCreator defaultInstanceCreator;
    private final MatcherForSingletonInstanceCreator matcherForSingletonInstanceCreator = new MatcherForSingletonInstanceCreator();

    public InstanceCreatorFactory() {
        matcherForInstanceList = new ArrayList<>();
        defaultInstanceCreator = new DefaultInstanceCreator();

        matcherForInstanceList.add(matcherForSingletonInstanceCreator.getSingletonInstanceCreatorClassMatcherForInstance());
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
