package com.reflecty.configurations;

import com.reflecty.annotations.Constant;
import com.reflecty.testModels.TestClass1;
import com.reflecty.testModels.TestClass2;
import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class NamespaceConstantTypeContainerTest {
    @Test
    public void matches_returnsTrueWhenTheDecoratedClassIsTheSameClassAndHasTheSameNamespace() throws Exception {
        NamespaceConstantTypeContainer<TestClass1> namespace = new NamespaceConstantTypeContainer<>("namespace", new TestClass1(), TestClass1.class);

        Constant constant = getConstant("namespace");

        assertThat(namespace.matches(new DecoratedClass<>(TestClass1.class, constant)), is(true));
    }

    @Test
    public void matches_returnsFalseWhenTheDecoratedClassIsTheSameClassAndHasADifferentNamespace() throws Exception {
        NamespaceConstantTypeContainer<TestClass1> namespace = new NamespaceConstantTypeContainer<>("namespace", new TestClass1(), TestClass1.class);

        Constant constant = getConstant("non-matching-namespace");

        assertThat(namespace.matches(new DecoratedClass<>(TestClass1.class, constant)), is(false));
    }

    @Test
    public void matches_returnsFalseWhenTheDecoratedClassIsADifferentClassButHastheSameNamespace() throws Exception {
        NamespaceConstantTypeContainer<TestClass1> namespace = new NamespaceConstantTypeContainer<>("namespace", new TestClass1(), TestClass1.class);

        Constant constant = getConstant("namespace");

        assertThat(namespace.matches(new DecoratedClass<>(TestClass2.class, constant)), is(false));
    }

    @Test
    public void matches_ifDecoratedClassHasNoAnnotation() throws Exception {
        NamespaceConstantTypeContainer<TestClass1> namespace = new NamespaceConstantTypeContainer<>("namespace", new TestClass1(), TestClass1.class);

        assertThat(namespace.matches(new DecoratedClass<>(TestClass2.class)), is(false));
    }

    private Constant getConstant(final String namespace) {
        return new Constant() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return Constant.class;
            }

            @Override
            public String value() {
                return namespace;
            }
        };
    }

}