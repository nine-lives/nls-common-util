package com.nls.util;

import com.google.common.base.Strings;

import java.net.URL;
import java.text.Normalizer;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Urls {

    private Urls() {

    }

    public static Map<String, List<String>> splitQuery(URL url) {
        if (Strings.isNullOrEmpty(url.getQuery())) {
            return Collections.emptyMap();
        }
        return Arrays.stream(url.getQuery().split("&"))
                .map(Urls::splitQueryParameter)
                .collect(Collectors.groupingBy(
                        AbstractMap.SimpleImmutableEntry::getKey,
                        LinkedHashMap::new,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }

    private static AbstractMap.SimpleImmutableEntry<String, String> splitQueryParameter(String it) {
        final int idx = it.indexOf("=");
        final String key = idx > 0 ? it.substring(0, idx) : it;
        final String value = idx > 0 && it.length() > idx + 1 ? it.substring(idx + 1) : null;
        return new AbstractMap.SimpleImmutableEntry<>(key, value);
    }

    public static String prependParams(String url, String params) {
        if (Strings.isNullOrEmpty(params) || params.trim().isEmpty()) {
            return url;
        }
        if (url.contains("?")) {
            String[] tokens = url.split("\\?");
            switch (tokens.length) {
                case 1:
                    return tokens[0] + "?" + params;
                case 2:
                    return tokens[0] + "?" + params + "&" + tokens[1];
                default:
                    return url; // something fishy so ignore tracking params
            }
        }
        return url + "?" + params;
    }

    public static String toPrettyURL(String string) {
        return Normalizer.normalize(string.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("[^\\p{Alnum}.]+", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("(^-)|(-$)", "");
    }
}
