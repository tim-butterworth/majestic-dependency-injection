package com.reflecty.functions;

import java.util.function.Function;

public class PassthroughFunction implements Function {
    @Override
    public Object apply(Object o) {
        return o;
    }
}
