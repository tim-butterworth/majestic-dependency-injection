package com.reflecty.integration.proxied;

import java.util.List;
import java.util.Map;

public interface WrappedMethodCallWithMultipleMethods {
    public Map<String, String> updateMap(Map<String, String> map);
    public List<String> updateMap(List<String> list);
}
