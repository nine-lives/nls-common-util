package com.nls.util;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FilterParser {

    private final Set<String> lookupKeywords;
    private final List<String> keyWords;

    public FilterParser(String filter) {
        List<String> tokens = split(filter);
        this.lookupKeywords = Collections.emptySet();
        this.keyWords = Collections.unmodifiableList(tokens);
    }

    public FilterParser(String filter, Set<String> lookupList) {
        Set<String> lookupKeywords = new HashSet<>();
        Map<String, String> normalisedLookupMap = lookupList.stream()
                .collect(Collectors.toMap(o -> o.toLowerCase().trim(), Function.identity()));

        List<String> tokens = split(filter);
        for (ListIterator<String> it = tokens.listIterator(); it.hasNext();) {
            String token = it.next().trim();

            if (token.isEmpty()) {
                continue;
            }

            if (normalisedLookupMap.containsKey(token.toLowerCase().trim())) {
                lookupKeywords.add(normalisedLookupMap.get(token.toLowerCase().trim()));
                it.remove();
            }
        }

        this.lookupKeywords = Collections.unmodifiableSet(lookupKeywords);
        this.keyWords = Collections.unmodifiableList(tokens);
    }


    public Set<String> getLookupKeywords() {
        return lookupKeywords;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public String getKeyWordAsString() {
        return Joiner.on(" ").join(keyWords);
    }

    private static List<String> split(String input) {
        List<String> result = new ArrayList<>();
        String regex = "\"([^\"]*)\"|(\\S+)";
        boolean addTrailingQuote = input.chars().filter(c -> c == '"').count() % 2 == 1;
        Matcher m = Pattern.compile(regex).matcher(input + (addTrailingQuote ? "\"" : ""));
        while (m.find()) {
            if (m.group(1) != null) {
                result.add(m.group(1));
            } else {
                result.add(m.group(2));
            }
        }
        return result;
    }
}
