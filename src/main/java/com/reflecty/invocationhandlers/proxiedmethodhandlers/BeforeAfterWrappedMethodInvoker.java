package com.reflecty.invocationhandlers.proxiedmethodhandlers;

import com.reflecty.annotations.DoAfter;
import com.reflecty.annotations.DoBefore;
import com.reflecty.functions.PassthroughFunction;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class BeforeAfterWrappedMethodInvoker<T> {

    public Object invokeBeforeAndAfterWrappedMethod(Object[] args, Method declaredMethod, T instance) throws InstantiationException, IllegalAccessException {
        Annotation[] declaredAnnotations = declaredMethod.getDeclaredAnnotations();

        List<Function> functions = getOrderedFunctionList(declaredMethod, instance, declaredAnnotations);

        final Object[] result = Arrays.asList(args).toArray();
        functions.forEach(fun -> result[0] = fun.apply(result));
        return result[0];
    }

    private List<Function> getOrderedFunctionList(Method declaredMethod, T instance, Annotation[] declaredAnnotations) throws InstantiationException, IllegalAccessException {
        Class<? extends Function> afterFunctionClass = getClassFromAnnotation(
                declaredAnnotations,
                annotation -> annotation instanceof DoAfter,
                annotation -> ((DoAfter) annotation).value()
        );

        Class<? extends Function> beforeFunctionClass = getClassFromAnnotation(
                declaredAnnotations,
                annotation -> annotation.annotationType().equals(DoBefore.class),
                annotation -> ((DoBefore) annotation).value()
        );

        Function afterFunction = afterFunctionClass.newInstance();
        Function beforeFunction = beforeFunctionClass.newInstance();

        return Arrays.asList(
                arguments -> runtimeExceptionWrap(afterFunction, extractApplyFunction(afterFunction), (Object[]) arguments),
                arguments -> runtimeExceptionWrap(instance, declaredMethod, (Object[]) arguments),
                arguments -> runtimeExceptionWrap(beforeFunction, extractApplyFunction(beforeFunction), (Object[]) arguments)
        );
    }

    public Object runtimeExceptionWrap(Object target, Method method, Object[] arguments) {
        try {
            return method.invoke(target, arguments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Method extractApplyFunction(Function beforeFunction) {
        return Arrays.asList(beforeFunction.getClass().getDeclaredMethods())
                .stream()
                .filter(m -> m.getName().equals("apply"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is not apply method on " + beforeFunction.getClass().getName()));
    }

    public Class<? extends Function> getClassFromAnnotation(
            Annotation[] declaredAnnotations,
            Predicate<Annotation> annotationPredicate,
            Function<Annotation, Class<? extends Function>> annotationFunction
    ) {
        return (Class<? extends Function>) Arrays.asList(declaredAnnotations).stream()
                .filter(annotationPredicate)
                .findFirst()
                .map(value -> new Class[]{annotationFunction.apply(value)})
                .orElse(new Class[]{PassthroughFunction.class})[0];
    }
}