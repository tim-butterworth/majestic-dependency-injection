package com.reflecty.testModels;

public class StatefulObject {
    private String usefulStringData;
    private Long usefulLongData;
    private Object genericUselessObject;

    public String getUsefulStringData() {
        return usefulStringData;
    }

    public void setUsefulStringData(String usefulStringData) {
        this.usefulStringData = usefulStringData;
    }

    public Long getUsefulLongData() {
        return usefulLongData;
    }

    public void setUsefulLongData(Long usefulLongData) {
        this.usefulLongData = usefulLongData;
    }

    public Object getGenericUselessObject() {
        return genericUselessObject;
    }

    public void setGenericUselessObject(Object genericUselessObject) {
        this.genericUselessObject = genericUselessObject;
    }
}
