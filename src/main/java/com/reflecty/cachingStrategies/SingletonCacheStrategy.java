package com.reflecty.cachingStrategies;

import com.reflecty.configurations.DecoratedClass;
import com.reflecty.instantiators.Instantiator;

import java.util.HashMap;
import java.util.Map;

public class SingletonCacheStrategy implements CachingStrategy {
    private final Map<Class<?>, Object> singletonObjectCache;

    public SingletonCacheStrategy() {
        this.singletonObjectCache = new HashMap<>();
    }

    @Override
    public <T> T getInstance(Instantiator instantiator, DecoratedClass<T> decoratedClass) {
        System.out.println("Thread " + Thread.currentThread().getName() + " is about to enter synchronized block");
        Class<T> tClass = decoratedClass.getContainedClass();
        synchronized (tClass) {
            return createOrGetFromCache(instantiator, decoratedClass, tClass);
        }
    }

    private <T> T createOrGetFromCache(Instantiator instantiator, DecoratedClass<T> decoratedClass, Class<T> tClass) {
        System.out.println("Thread " + Thread.currentThread().getName() + " entered synchronized block......" + tClass.getName());
        T instance = (T) singletonObjectCache.get(decoratedClass.getContainedClass());
        if (notAlreadyCached(tClass)) {
            sleepForALittleWhile();
            System.out.println("Entries in the map -> " + singletonObjectCache.entrySet().size());
            instance = instantiator.instantiate(decoratedClass);
            singletonObjectCache.put(tClass, instance);
        }
        System.out.println("exited synchronized block.....");
        return instance;
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
