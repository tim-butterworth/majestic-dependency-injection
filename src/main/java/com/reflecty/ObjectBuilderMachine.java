package com.reflecty;

import com.reflecty.creators.InstanceCreatorMachine;

import java.util.List;

public class ObjectBuilderMachine {
    private List<InstanceCreatorMachine> instanceCreatorList;
    private InstanceCreatorMachine defaultInstanceCreator;

    public ObjectBuilderMachine(
            List<InstanceCreatorMachine> instanceCreatorList,
            InstanceCreatorMachine defaultInstanceCreator
    ) {
        this.instanceCreatorList = instanceCreatorList;
        this.defaultInstanceCreator = defaultInstanceCreator;
    }

    public <T> T getInstance(Class<T> clazz) {
        return getInstanceCreator(clazz).getInstance(clazz);
    }

    private <T> InstanceCreatorMachine getInstanceCreator(Class<T> clazz) {
        return instanceCreatorList.stream().filter(creator -> creator.canHandle(clazz)).findFirst().orElse(defaultInstanceCreator);
    }

}
