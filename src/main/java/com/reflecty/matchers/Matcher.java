package com.reflecty.matchers;

public interface Matcher {
    <T> boolean canHandle(Class<T> clazz);
}
