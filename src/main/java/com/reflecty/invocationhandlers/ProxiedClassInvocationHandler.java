package com.reflecty.invocationhandlers;

import com.reflecty.invocationhandlers.proxiedmethodhandlers.BeforeAfterWrappedMethodInvoker;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.function.Predicate;

public class ProxiedClassInvocationHandler<T> implements InvocationHandler {

    private final BeforeAfterWrappedMethodInvoker<T> beforeAfterWrappedMethodInvoker;
    private T instance;

    public ProxiedClassInvocationHandler(T instance) {
        this.instance = instance;
        beforeAfterWrappedMethodInvoker = new BeforeAfterWrappedMethodInvoker<>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method declaredMethod = instance.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());

        if (declaredMethod.getParameterTypes().length > 0) {
            return beforeAfterWrappedMethodInvoker.invokeBeforeAndAfterWrappedMethod(args, declaredMethod, instance);
        }
        return declaredMethod.invoke(instance);
    }

}
