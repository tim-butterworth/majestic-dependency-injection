package com.reflecty.matchers;

public class EverythingMatcher implements Matcher {
    @Override
    public <T> boolean canHandle(Class<T> clazz) {
        return true;
    }
}
