package com.reflecty.integration.proxied;

import java.util.Map;
import java.util.function.Function;

public class MakeMapEntry implements Function<Map<String, String>, Map<String, String>> {
    @Override
    public Map<String, String> apply(Map<String, String>  map) {
        map.put(this.getClass().getName(), "Value!!!");
        return map;
    }
}
