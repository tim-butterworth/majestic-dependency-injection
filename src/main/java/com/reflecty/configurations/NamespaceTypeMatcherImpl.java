package com.reflecty.configurations;

public class NamespaceTypeMatcherImpl<T> implements TypeMatcher<T> {
    private final String namespace;
    private final Class<T> aClass;

    public NamespaceTypeMatcherImpl(String namespace, Class<T> aClass) {
        this.namespace = namespace;
        this.aClass = aClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NamespaceTypeMatcherImpl that = (NamespaceTypeMatcherImpl) o;

        if (namespace != null ? !namespace.equals(that.namespace) : that.namespace != null) return false;
        return aClass != null ? aClass.equals(that.aClass) : that.aClass == null;

    }

    @Override
    public int hashCode() {
        int result = namespace != null ? namespace.hashCode() : 0;
        result = 31 * result + (aClass != null ? aClass.hashCode() : 0);
        return result;
    }
}
