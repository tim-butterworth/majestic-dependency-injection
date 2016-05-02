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
    public <T> T getInstance(DecoratedClass<T> classContainer, Instantiator instantiator) {
        System.out.println("Thread " + Thread.currentThread().getName() + " is about to enter synchronized block");
        Class<T> containedClass = classContainer.getContainedClass();
        synchronized (containedClass) {
            System.out.println("Thread " + Thread.currentThread().getName() + " entered synchronized block......" + containedClass.getName());
            sleepForALittleWhile();

            T instance = (T) singletonObjectCache.get(containedClass);
            if (notAlreadyCached(containedClass)) {
                System.out.println("Entries in the map -> " + singletonObjectCache.entrySet().size());
                instance = instantiator.instantiate(classContainer);
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
