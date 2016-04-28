package com.reflecty;

import com.reflecty.configurations.BuildModule;
import com.reflecty.configurations.DefaultModule;
import com.reflecty.factories.InstanceCreatorFactory;
import com.reflecty.instantiators.ReflectiveInstantiator;
import com.reflecty.helperObjects.ObjectContainer;

public class ObjectBuilderMachineBuilder {

    private BuildModule module;

    public ObjectBuilderMachine build() {
        ObjectContainer<ObjectBuilderMachine> objectBuilderMachineObjectContainer = new ObjectContainer<>();

        if(module==null) module = new DefaultModule();
        ReflectiveInstantiator reflectiveInstantiator = new ReflectiveInstantiator(objectBuilderMachineObjectContainer, module);
        InstanceCreatorFactory instanceCreatorFactory = new InstanceCreatorFactory(reflectiveInstantiator);
        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachine(instanceCreatorFactory);

        objectBuilderMachineObjectContainer.addToContainer(objectBuilderMachine);

        return objectBuilderMachine;
    }

    public ObjectBuilderMachineBuilder addModule(BuildModule module) {
        this.module = module;
        return this;
    }
}
