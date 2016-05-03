package com.reflecty.creators;

import com.reflecty.configurations.DecoratedClass;
import com.reflecty.instantiators.ReflectiveInstantiator;

import java.util.HashMap;
import java.util.Map;

public class SingletonInstanceCreator implements InstanceCreator {
    private final Map<Class<?>, Object> singletonObjectCache;
    private final ReflectiveInstantiator reflectiveInstantiator;

    public SingletonInstanceCreator(ReflectiveInstantiator reflectiveInstantiator) {
        this.reflectiveInstantiator = reflectiveInstantiator;
        singletonObjectCache = new HashMap<>();
    }

    @Override
    public <T> T getInstance(DecoratedClass<T> classContainer) {
        System.out.println("Thread " + Thread.currentThread().getName() + " is about to enter synchronized block");
        Class<T> containedClass = classContainer.getContainedClass();

        synchronized (containedClass) {
            System.out.println("Thread " + Thread.currentThread().getName() + " entered synchronized block......" + containedClass.getName());
            sleepForALittleWhile();

            T instance = (T) singletonObjectCache.get(classContainer);
            if (notAlreadyCached(containedClass)) {
                System.out.println("Entries in the map -> " + singletonObjectCache.entrySet().size());
                instance = reflectiveInstantiator.instantiate(classContainer.getContainedClass());
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