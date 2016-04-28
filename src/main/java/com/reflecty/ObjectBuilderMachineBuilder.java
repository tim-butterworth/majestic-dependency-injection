package com.reflecty;

import com.reflecty.creators.ReflectiveInstantiator;
import com.reflecty.helperObjects.ObjectContainer;

public class ObjectBuilderMachineBuilder {

    public static ObjectBuilderMachine build() {
        ObjectContainer<ObjectBuilderMachine> objectBuilderMachineObjectContainer = new ObjectContainer<>();
        ReflectiveInstantiator reflectiveInstantiator = new ReflectiveInstantiator(objectBuilderMachineObjectContainer);
        InstanceCreatorFactory instanceCreatorFactory = new InstanceCreatorFactory(reflectiveInstantiator);
        ObjectBuilderMachine objectBuilderMachine = new ObjectBuilderMachine(instanceCreatorFactory);

        objectBuilderMachineObjectContainer.addToContainer(objectBuilderMachine);

        return objectBuilderMachine;
    }

}
