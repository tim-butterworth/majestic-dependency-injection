package com.reflecty.matchers;

import java.util.function.Function;

public class ObjectMatcher<M, T> {

    private Function<M, Boolean> fun;
    private final T t;

    public ObjectMatcher(Function<M, Boolean> fun, T t) {
        this.fun = fun;
        this.t = t;
    }

    public boolean matches(M value) {
        return fun.apply(value);
    }

    public T getObject() {
        return t;
    }

}
