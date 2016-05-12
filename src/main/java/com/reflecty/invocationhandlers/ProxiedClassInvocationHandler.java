package com.reflecty.invocationhandlers;

import com.reflecty.annotations.DoAfter;
import com.reflecty.annotations.DoBefore;
import com.reflecty.functions.PassthroughFunction;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public class ProxiedClassInvocationHandler<T> implements InvocationHandler {

    private T instance;

    public ProxiedClassInvocationHandler(T instance) {
        this.instance = instance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method declaredMethod = instance.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());

        if (declaredMethod.getParameterTypes().length > 0) {
            Annotation[] declaredAnnotations = declaredMethod.getDeclaredAnnotations();

            Class<? extends Function> doBeforeFunctions = getClassFromAnnotation(
                    declaredAnnotations,
                    annotation -> annotation instanceof DoBefore,
                    annotation -> arrayWrapClass(((DoBefore) annotation).value())
            );

            Class<? extends Function> doAfterFunctions = getClassFromAnnotation(
                    declaredAnnotations,
                    annotation -> annotation instanceof DoAfter,
                    annotation -> arrayWrapClass(((DoAfter) annotation).value())
            );

            Class<? extends Function> afterFunctionClass = doAfterFunctions;
            Class<? extends Function> beforeFunctionClass = doBeforeFunctions;

            Function afterFunction = afterFunctionClass.newInstance();
            Function beforeFunction = beforeFunctionClass.newInstance();

            Method beforeApply = getApplyMethod(beforeFunction);
            Method afterApply = getApplyMethod(afterFunction);

            final Object[] result = Arrays.asList(args).toArray();
            Arrays.asList(
                    (Function) arguments -> safeInvoke(afterFunction, afterApply, (Object[]) arguments),
                    arguments -> safeInvoke(instance, declaredMethod, (Object[]) arguments),
                    arguments -> safeInvoke(beforeFunction, beforeApply, (Object[]) arguments)
            ).forEach(fun -> result[0] = fun.apply(result));

            return result[0];
        }
        return declaredMethod.invoke(instance);
    }

    private Class[] arrayWrapClass(Class<? extends Function> clazz) {
        return new Class[]{clazz};
    }

    private Object safeInvoke(Object target, Method method, Object[] arguments) {
        try {
            return method.invoke(target, arguments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Method getApplyMethod(Function beforeFunction) {
        return Arrays.asList(beforeFunction.getClass().getDeclaredMethods())
                .stream()
                .filter(m -> m.getName().equals("apply"))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private Class<? extends Function> getClassFromAnnotation(
            Annotation[] declaredAnnotations,
            Predicate<Annotation> annotationPredicate,
            Function<Annotation, Class<? extends Function>[]> annotationFunction
    ) {
        return Arrays.asList(declaredAnnotations).stream()
                .filter(annotationPredicate)
                .findFirst()
                .map(annotationFunction)
                .orElse(new Class[]{PassthroughFunction.class})[0];
    }
}
