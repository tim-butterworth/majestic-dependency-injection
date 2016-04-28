package com.reflecty.configurations;

public class NamespaceTypeMatcherImpl implements TypeMatcher {
    private final String key;
    private final Class<?> aClass;

    public NamespaceTypeMatcherImpl(String key, Class<?> aClass) {
        this.key = key;
        this.aClass = aClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NamespaceTypeMatcherImpl that = (NamespaceTypeMatcherImpl) o;

        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        return aClass != null ? aClass.equals(that.aClass) : that.aClass == null;

    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (aClass != null ? aClass.hashCode() : 0);
        return result;
    }
}
