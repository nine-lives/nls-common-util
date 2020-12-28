package com.nls.util;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public final class Emails {
    private static final Pattern DOMAIN_FORMAT_PATTERN = Pattern.compile("^@.+\\..+$");
    private static final Pattern EMAIL_FORMAT_PATTERN = Pattern.compile("^.+@.+\\..+$");

    private Emails() {
    }

    public static String toCsv(String value) {
        return Joiner.on(',').join(asList(value));
    }

    public static String toUniqueCsv(String value) {
        return Joiner.on(',').join(asSet(value));
    }

    public static List<String> asList(String value) {
        List<String> emails = new ArrayList<>();
        String[] tokens = Strings.nullToEmpty(value).split("[\n;, ]");
        for (String tem : tokens) {
            String em = tem.trim();
            if (!Strings.isNullOrEmpty(em)) {
                emails.add(normalise(em));
            }
        }
        return emails;
    }

    public static Set<String> asSet(String value) {
        return new LinkedHashSet<>(asList(value));
    }

    public static String normalise(String email) {
        return stripTrailingSlashes(Strings.nullToEmpty(email)).toLowerCase().trim();
    }

    public static boolean valid(String email) {
        return normalise(email).matches(EMAIL_FORMAT_PATTERN.pattern());
    }

    public static boolean validDomain(String email) {
        return normalise(email).matches(DOMAIN_FORMAT_PATTERN.pattern());
    }

    public static boolean validDomainOrEmail(String email) {
        return valid(email) || validDomain(email);
    }

    public static String stripTrailingSlashes(String email) {
        return email.replaceAll("/+$", "");
    }
}
