package com.nls.web;

import java.util.ArrayList;
import java.util.List;

public class CspValueGroup {
    private final String name;
    private final List<CspValue> values = new ArrayList<>();

    public CspValueGroup(String name) {
        this.name = name;
    }

    public CspValueGroup add(CspValue value) {
        this.values.add(value);
        return this;
    }

    public CspValueGroup add(CspValueGroup group) {
        this.values.addAll(group.getValues());
        return this;
    }

    public String getName() {
        return name;
    }

    public List<CspValue> getValues() {
        return values;
    }
}
