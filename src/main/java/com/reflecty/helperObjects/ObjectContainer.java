package com.reflecty.helperObjects;

public class ObjectContainer<T> {
    private T contents;

    public void addToContainer(T contents) {
        this.contents = contents;
    }

    public T getContents() {
        return contents;
    }
}
