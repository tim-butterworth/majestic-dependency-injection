package com.reflecty.cachingStrategies;

import com.reflecty.instantiators.Instantiator;

import java.util.HashMap;
import java.util.Map;

public class SingletonCacheStrategy implements CachingStrategy {
    private final Map<Class<?>, Object> singletonObjectCache;

    public SingletonCacheStrategy() {
        this.singletonObjectCache = new HashMap<>();
    }

    @Override
    public <T> T getInstance(Instantiator instantiator, Class<T> containedClass) {
        System.out.println("Thread " + Thread.currentThread().getName() + " is about to enter synchronized block");
        synchronized (containedClass) {
            System.out.println("Thread " + Thread.currentThread().getName() + " entered synchronized block......" + containedClass.getName());
            sleepForALittleWhile();

            T instance = (T) singletonObjectCache.get(containedClass);
            if (notAlreadyCached(containedClass)) {
                System.out.println("Entries in the map -> " + singletonObjectCache.entrySet().size());
                instance = instantiator.instantiate(containedClass);
                singletonObjectCache.put(containedClass, instance);
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
