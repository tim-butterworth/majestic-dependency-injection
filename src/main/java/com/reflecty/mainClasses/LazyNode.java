package com.reflecty.mainClasses;

public class LazyNode {

    private LazyNodeProvider provider;
    private LazyNode nextNode;

    public LazyNode(LazyNodeProvider provider) {
        this.provider = provider;
    }

    public LazyNode getNextNode() {
        if(nextNode==null) {
            nextNode = provider.getInstance();
        }
        return nextNode;
    }
}
