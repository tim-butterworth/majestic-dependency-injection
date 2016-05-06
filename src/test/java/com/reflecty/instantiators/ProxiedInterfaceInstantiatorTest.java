package com.reflecty.instantiators;

import com.reflecty.configurations.DecoratedClass;
import com.reflecty.testModels.RandomSingletonStringInterface;
import com.reflecty.testModels.RandomStringInterface;
import com.reflecty.testModels.StatefulObject;
import com.reflecty.testModels.StatefulObjectProvider;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

public class ProxiedInterfaceInstantiatorTest {

    private ProxiedInterfaceInstantiator proxiedInterfaceInstantiator;

    @Before
    public void setUp() throws Exception {
        proxiedInterfaceInstantiator = new ProxiedInterfaceInstantiator();
    }

    @Test
    public void instantiate_randomAnnotatedWithStringMethod() throws Exception {
        RandomStringInterface instantiate = proxiedInterfaceInstantiator.instantiate(new DecoratedClass<>(RandomStringInterface.class));

        assertThat(instantiate.getAString(), not(nullValue()));
    }

    @Test
    public void instantiate_providersProvideObjects() throws Exception {
        StatefulObjectProvider statefulObjectProvider = proxiedInterfaceInstantiator.instantiate(new DecoratedClass<>(StatefulObjectProvider.class));

        StatefulObject instance = statefulObjectProvider.getInstance();

        assertThat(instance, not(nullValue()));
    }
}