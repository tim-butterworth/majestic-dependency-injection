package com.reflecty.configurations;

import com.reflecty.annotations.Constant;
import com.reflecty.annotations.Namespace;
import com.reflecty.testModels.TestClass1;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.annotation.Annotation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class ConstantModuleTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ConstantModule constantModule;

    @Before
    public void setUp() throws Exception {
        constantModule = new ConstantModule();
    }

    @Test
    public void findMatch_returnsTheMatchingConstant() throws Exception {
        TestClass1 expectedConstant = new TestClass1();

        ConstantTypeContainer<TestClass1> tConstantTypeMatcher = new ConstantTypeContainerImpl<>(expectedConstant, TestClass1.class);

        constantModule.register(tConstantTypeMatcher);

        TestClass1 actualConstant = constantModule.findMatch(new DecoratedClass<>(TestClass1.class));

        assertThat(actualConstant, sameInstance(expectedConstant));
    }

    @Test
    public void findMatch_throwsARuntimeExceptionIfTheClassHasNeverBeenRegistered() throws Exception {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(equalTo("There is no constant registered for this type"));

        constantModule.findMatch(new DecoratedClass<>(String.class));
    }

    @Test
    public void findMatch_throwsARuntimeExceptionIfTheClassHasBeenRegisteredButThereAreNoMatches() throws Exception {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(equalTo("No match was found"));

        constantModule.register(new NamespaceConstantTypeContainer<>("namespace", "value", String.class));
        constantModule.findMatch(new DecoratedClass<>(String.class));
    }

    @Test
    public void findMatch_theSecondMatchingConstantShouldReplaceTheFirst() throws Exception {
        constantModule.register(new ConstantTypeContainerImpl<>("string 1", String.class));
        constantModule.register(new ConstantTypeContainerImpl<>("string 2", String.class));
        constantModule.register(new ConstantTypeContainerImpl<>("string 3", String.class));
        constantModule.register(new ConstantTypeContainerImpl<>("string 4", String.class));
        constantModule.register(new ConstantTypeContainerImpl<>("string 1", String.class));

        String match = constantModule.findMatch(new DecoratedClass<>(String.class));

        assertThat(match, is("string 1"));
    }

    @Test
    public void findMatch_theSecondMatchingNamespacedConstantShouldReplaceTheFirst() throws Exception {
        constantModule.register(new NamespaceConstantTypeContainer<>("constant.namespace", "string 1", String.class));
        constantModule.register(new NamespaceConstantTypeContainer<>("constant.namespace", "string 2", String.class));

        Constant namespaceAnnotation = new Constant() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return Constant.class;
            }

            @Override
            public String value() {
                return "constant.namespace";
            }
        };

        String match = constantModule.findMatch(new DecoratedClass<>(String.class, namespaceAnnotation));

        assertThat(match, is("string 2"));
    }

    @Test
    public void findMatch_returnsTheMatchingNamespacedConstant() throws Exception {
        TestClass1 expectedConstant = new TestClass1();

        NamespaceConstantTypeContainer<TestClass1> tConstantTypeMatcher = new NamespaceConstantTypeContainer<>("some.name", expectedConstant, TestClass1.class);

        constantModule.register(tConstantTypeMatcher);

        Constant constant = new Constant() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return Constant.class;
            }

            @Override
            public String value() {
                return "some.name";
            }
        };

        TestClass1 actualConstant = constantModule.findMatch(new DecoratedClass<>(TestClass1.class, constant));

        assertThat(actualConstant, sameInstance(expectedConstant));
    }
}