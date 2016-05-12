package com.reflecty.integration.proxied;

import java.util.List;
import java.util.function.Function;

public class MakeListEntryBefore implements Function<List<String>,List<String>> {
    @Override
    public List<String> apply(List<String> strings) {
        strings.add(this.getClass().getCanonicalName());
        return strings;
    }
}
