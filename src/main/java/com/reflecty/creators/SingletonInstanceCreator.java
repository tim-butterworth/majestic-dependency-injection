package com.reflecty.creators;

import java.util.HashMap;
import java.util.Map;

public class SingletonInstanceCreator implements InstanceCreator {
    private final Map<Class<?>, Object> singletonObjectCache;
    private final ReflectiveInstantiator reflectiveInstantiator;

    public SingletonInstanceCreator() {
        reflectiveInstantiator = new ReflectiveInstantiator();
        singletonObjectCache = new HashMap<>();
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        System.out.println("Thread " + Thread.currentThread().getName() + " is about to enter synchronized block");
        synchronized (clazz) {
            System.out.println("Thread " + Thread.currentThread().getName() + " entered synchronized block......" + clazz.getName());
            sleepForALittleWhile();

            T instance = (T) singletonObjectCache.get(clazz);
            if (notAlreadyCached(clazz)) {
                System.out.println("Entries in the map -> " + singletonObjectCache.entrySet().size());
                instance = reflectiveInstantiator.instantiate(clazz);
                singletonObjectCache.put(clazz, instance);
            }

            System.out.println("exited synchronized block.....");
            return instance;
        }

    }

    private void sleepForALittleWhile() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private <T> boolean notAlreadyCached(Class<T> clazz) {
        return singletonObjectCache.get(clazz) == null;
    }

}