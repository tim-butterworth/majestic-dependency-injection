package com.reflecty.configurations;

import java.util.HashSet;
import java.util.Set;

public class ConstantModule {

    private Set<MatchingContainer<?>> matcherSet;

    public ConstantModule() {
        matcherSet = new HashSet<>();
    }

    public <T> T findMatch(DecoratedClass<?> decoratedClass) {
        MatchingContainer<T> matchingContainer = matcherSet.stream()
                .filter(matcher -> matcher.matches(decoratedClass))
                .map(container -> (MatchingContainer<T>) container)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No match was found"));

        return matchingContainer.getContent();
    }

    public <T> void register(MatchingContainer<T> tConstantTypeMatcher) {
        if(!matcherSet.add(tConstantTypeMatcher)) {
            matcherSet.remove(tConstantTypeMatcher);
            matcherSet.add(tConstantTypeMatcher);
        }
    }
}
