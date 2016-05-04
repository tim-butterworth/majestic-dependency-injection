package com.reflecty.configurations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConstantModule {

    private Map<Class<?>, Set<ConstantTypeContainer<?>>> registeredConstants;

    public ConstantModule() {
        registeredConstants = new HashMap<>();
    }

    public <T> T findMatch(DecoratedClass<?> decoratedClass) {
        Class<?> containedClass = decoratedClass.getContainedClass();

        Set<ConstantTypeContainer<?>> constantTypeMatchers = registeredConstants.get(containedClass);
        if(constantTypeMatchers==null) throw new RuntimeException("There is no constant registered for this type");

        ConstantTypeContainer<T> constantTypeContainer = constantTypeMatchers.stream()
                .filter(matcher -> matcher.matches(decoratedClass))
                .map(container -> (ConstantTypeContainer<T>) container)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No match was found"));

        return constantTypeContainer.getConstant();
    }

    public <T> void register(ConstantTypeContainer<T> tConstantTypeMatcher) {
        Class<?> aClass = tConstantTypeMatcher.getConstantClass();

        Set<ConstantTypeContainer<?>> constantTypeMatchers = registeredConstants.get(aClass);
        if (constantTypeMatchers == null) {
            constantTypeMatchers = new HashSet<>();
            registeredConstants.put(aClass, constantTypeMatchers);
        }
        constantTypeMatchers.add(tConstantTypeMatcher);
    }
}
