package com.reflecty.instantiators;

import com.reflecty.configurations.DecoratedClass;
import com.reflecty.invocationhandlers.ProxiedClassInvocationHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class ProxiedClassInstantiator implements Instantiator {

    private final CreateObjectFromConstructor objectBuilderMachineObjectContainer;

    public ProxiedClassInstantiator(CreateObjectFromConstructor objectBuilderMachineObjectContainer) {
        this.objectBuilderMachineObjectContainer = objectBuilderMachineObjectContainer;
    }

    @Override
    public <T> T instantiate(DecoratedClass<T> decoratedClass) {
        Class<T> containedClass = decoratedClass.getContainedClass();
        Class<?> interfaceClass = containedClass.getInterfaces()[0];

        Constructor<?> constructor = containedClass.getDeclaredConstructors()[0];
        Object[] objects = objectBuilderMachineObjectContainer.buildConstructorArguments(constructor);

        try {
            T instance = (T) constructor.newInstance(objects);
            ProxiedClassInvocationHandler<T> invocationHandler = getInvocationHandler(instance);
            return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{interfaceClass}, invocationHandler);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    private <T> ProxiedClassInvocationHandler<T> getInvocationHandler(T instance) {
        return new ProxiedClassInvocationHandler<>(instance);
    }
}
