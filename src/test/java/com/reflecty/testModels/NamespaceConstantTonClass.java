package com.reflecty.testModels;

import com.reflecty.annotations.Constant;

public class NamespaceConstantTonClass {

    private final String one;
    private final Long two;

    public NamespaceConstantTonClass(
            @Constant("value.that.is.constant.one") String one,
            @Constant("value.that.is.constant.two") Long two
    ) {
        this.one = one;
        this.two = two;
    }

    public String getOne() {
        return one;
    }

    public Long getTwo() {
        return two;
    }
}
