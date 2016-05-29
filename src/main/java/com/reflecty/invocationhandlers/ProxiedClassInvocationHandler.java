package com.reflecty.invocationhandlers;

import com.reflecty.invocationhandlers.proxiedmethodhandlers.BeforeAfterWrappedMethodInvoker;
import com.reflecty.invocationhandlers.proxiedmethodhandlers.EmptyMethodInvoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxiedClassInvocationHandler<T> implements InvocationHandler {

    private final BeforeAfterWrappedMethodInvoker<T> beforeAfterWrappedMethodInvoker;
    private final T instance;
    private final EmptyMethodInvoker<T> emptyMethodInvoker;

    public ProxiedClassInvocationHandler(T instance) {
        this.instance = instance;
        beforeAfterWrappedMethodInvoker = new BeforeAfterWrappedMethodInvoker<>();
        emptyMethodInvoker = new EmptyMethodInvoker<>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method declaredMethod = instance.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
        if(declaredMethod.getParameterTypes().length>0) return beforeAfterWrappedMethodInvoker.invokeMethod(args, declaredMethod, instance);
        else return emptyMethodInvoker.invokeMethod(declaredMethod, instance);
    }

}
