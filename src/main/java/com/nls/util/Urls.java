package com.nls.util;

import com.google.common.base.Strings;

import java.text.Normalizer;

public final class Urls {

    private Urls() {

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
                .replaceAll("[^\\p{Alnum}]+", "-");
    }
}
