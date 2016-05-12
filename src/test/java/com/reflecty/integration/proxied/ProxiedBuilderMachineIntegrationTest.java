package com.reflecty.integration.proxied;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.builders.ObjectBuilderMachineBuilder;
import com.reflecty.testModels.ConfigureableObject;
import com.reflecty.testModels.proxiedModels.WrappedMethodCall;
import com.reflecty.testModels.proxiedModels.WrappedMethodCallImpl;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProxiedBuilderMachineIntegrationTest {

    @Test
    public void createAWrappedClassUsingProxiesThatFunctionsAreExecutedBeforeAndAfter1() throws Exception {
        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachineBuilder().build();

        WrappedMethodCall instance = objectBuilderMachine.getInstance(WrappedMethodCallImpl.class);

        ConfigureableObject configureableObject = instance.doTheThing(new ConfigureableObject());

        assertThat(configureableObject.getStringFromAfter(), is("This string was added after the method invocation."));
        assertThat(configureableObject.getStringFromBefore(), is("Setting the string to be before the method invocation"));
        assertThat(configureableObject.getStringFromMethod(), is("This string comes from the method call"));
    }

    @Test
    public void createAWrappedClassUsingProxiesThatFunctionsAreExecutedBeforeAndAfter2() throws Exception {
        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachineBuilder().build();

        WrappedMethodCallWithMultipleMethods wrappedMethodCallWithMultipleMethods = objectBuilderMachine.getInstance(WrappedMethodCallWithMultipleMethodsArgumentImpl.class);

        Map<String, String> map = new HashMap<>();
        Map<String, String> stringStringMap = wrappedMethodCallWithMultipleMethods.updateMap(map);

        assertThat(stringStringMap.entrySet().size(), is(2));
    }

    @Test
    public void createAWrappedClassUsingProxiesThatDoesTiming() throws Exception {
        //something like @Timed on particular methods
    }

    @Test
    public void createAWrappedClassUsingProxiesThatCreatesANewInstanceEachTime() throws Exception {
        //something like @DoBefore, @DoAfter
    }
}
