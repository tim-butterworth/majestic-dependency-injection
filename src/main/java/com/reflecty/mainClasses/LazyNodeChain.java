package com.reflecty.mainClasses;

public class LazyNodeChain {

    private LazyNode lazyNode;

    public LazyNodeChain(LazyNode lazyNode) {
        this.lazyNode = lazyNode;
    }

    public LazyNode getLazyNode() {
        return lazyNode;
    }
}
