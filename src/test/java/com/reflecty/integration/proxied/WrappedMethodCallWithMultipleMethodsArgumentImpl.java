package com.reflecty.integration.proxied;

import com.reflecty.annotations.DoAfter;
import com.reflecty.annotations.DoBefore;
import com.reflecty.annotations.Proxied;

import java.util.List;
import java.util.Map;

@Proxied
public class WrappedMethodCallWithMultipleMethodsArgumentImpl implements WrappedMethodCallWithMultipleMethods {
    @Override
    @DoBefore(MakeMapEntry.class)
    public Map<String, String> updateMap(Map<String, String> map) {
        map.put(this.getClass().getName(), "Value--");
        return map;
    }

    @Override
    @DoBefore(MakeListEntryBefore.class)
    @DoAfter(MakeListEntryAfter.class)
    public List<String> updateMap(List<String> list) {
        return null;
    }
}
