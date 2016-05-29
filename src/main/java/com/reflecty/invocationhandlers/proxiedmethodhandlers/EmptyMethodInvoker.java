package com.reflecty.invocationhandlers.proxiedmethodhandlers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EmptyMethodInvoker<T> {
    public T invokeMethod(Method declaredMethod, T instance) {
        try {
            return (T) declaredMethod.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
