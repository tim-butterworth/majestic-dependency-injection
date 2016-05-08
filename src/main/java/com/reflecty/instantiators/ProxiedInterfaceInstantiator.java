package com.reflecty.instantiators;

import com.reflecty.configurations.DecoratedClass;
import com.reflecty.configurations.ObjectProvider;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Random;

public class ProxiedInterfaceInstantiator implements Instantiator {
    @Override
    public <T> T instantiate(DecoratedClass<T> decoratedClass) {
        Boolean isObjectProvider = Arrays.asList(decoratedClass.getContainedClass().getInterfaces()).stream()
                .filter(clazz -> clazz.equals(ObjectProvider.class))
                .findFirst()
                .map(obj -> true)
                .orElse(false);

        Class<T> containedClass = decoratedClass.getContainedClass();
        if (isObjectProvider) {
            Class<?> type = (Class<?>) ((ParameterizedTypeImpl) containedClass.getGenericInterfaces()[0]).getActualTypeArguments()[0];
            return getProxyInstance(containedClass, ((a, b, c) -> type.newInstance()));
        }

        String randomString = getRandomString();
        return getProxyInstance(containedClass, (proxy, method, args) -> randomString);
    }

    private <T> T getProxyInstance(Class<T> containedClass, InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class<?>[]{containedClass},
                handler
        );
    }

    private String getRandomString() {
        return String.valueOf(new Random().nextLong());
    }
}
