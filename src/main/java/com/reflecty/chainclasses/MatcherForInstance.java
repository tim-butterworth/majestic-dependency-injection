package com.reflecty.chainclasses;

public interface MatcherForInstance<T, M> {

    boolean matches(M arg);
    T getInstance();
}
