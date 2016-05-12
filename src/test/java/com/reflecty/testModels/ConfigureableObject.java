package com.reflecty.testModels;

public class ConfigureableObject {
    private String stringFromBefore;
    private String stringFromMiddle;
    private String stringFromAfter;

    public String getStringFromBefore() {
        return stringFromBefore;
    }

    public void setStringFromBefore(String stringFromBefore) {
        this.stringFromBefore = stringFromBefore;
    }

    public String getStringFromMethod() {
        return stringFromMiddle;
    }

    public void setStringFromMethod(String stringFromMiddle) {
        this.stringFromMiddle = stringFromMiddle;
    }

    public String getStringFromAfter() {
        return stringFromAfter;
    }

    public void setStringFromAfter(String stringFromAfter) {
        this.stringFromAfter = stringFromAfter;
    }
}
