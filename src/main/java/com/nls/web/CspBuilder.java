package com.nls.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CspBuilder {

    private final List<CspValue> values = new ArrayList<>();
    private String reportUri;

    public CspBuilder add(CspValue value) {
        values.add(value);
        return this;
    }

    public CspBuilder add(CspValueGroup group) {
        this.values.addAll(group.getValues());
        return this;
    }

    public CspBuilder reportUri(String reportUri) {
        this.reportUri = reportUri;
        return this;
    }

    public String policy() {
        Map<String, List<String>> policyMap = values.stream()
                .map(CspValue::getDirectiveMap)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        List<String> directives = policyMap.keySet().stream()
                .sorted(Comparator.comparing(Function.identity()))
                .collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();
        for (String directive : directives) {
            builder
                    .append(directive)
                    .append(" ")
                    .append(policyMap.get(directive).stream()
                            .distinct()
                            .sorted(Comparator.comparing(Function.identity()))
                            .collect(Collectors.joining(" ")))
                    .append(";\n");
        }

        if (reportUri != null && !reportUri.isBlank()) {
            builder.append("report-uri ").append(reportUri).append(";");
        }

        return builder.toString();
    }
}
