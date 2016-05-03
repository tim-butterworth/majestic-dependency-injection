package com.reflecty.configurations;

import com.reflecty.matchers.ConstantTypeContainer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConstantModule {

    private Map<Class<?>, Set<ConstantTypeContainer<?>>> registeredConstants;

    public ConstantModule() {
        registeredConstants = new HashMap<>();
    }

    public <T> T findMatch(Class<T> tClass) {
        Set<ConstantTypeContainer<?>> constantTypeMatchers = registeredConstants.get(tClass);
        ConstantTypeContainer<T> constantTypeContainer = constantTypeMatchers.stream()
                .filter(matcher -> matcher.matches(tClass))
                .map(container -> (ConstantTypeContainer<T>) container)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(""));

        return constantTypeContainer.getConstant();
    }

    public <T> void register(Class<T> testClass1Class, ConstantTypeContainer<T> tConstantTypeMatcher) {
        Set<ConstantTypeContainer<?>> constantTypeMatchers = registeredConstants.get(testClass1Class);
        if (constantTypeMatchers == null) {
            constantTypeMatchers = new HashSet<>();
            registeredConstants.put(testClass1Class, constantTypeMatchers);
        }
        constantTypeMatchers.add(tConstantTypeMatcher);
    }
}
