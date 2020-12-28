package com.nls.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public final class Check {
    public static final String URL_PATTERN = "http(s)?://.+";

    private Check() {

    }

    public static void len(String value, String fieldName, int maxLength) {
        if (Strings.isNullOrEmpty(value)) {
            return;
        }
        Preconditions.checkArgument(value.length() <= maxLength, String.format(
                "field [%s] max allowed length is [%s], actual length is [%s] value is [%s]",
                fieldName, maxLength, value.length(), value));
    }

    public static void lenBetween(String value, String fieldName, int minLength, int maxLength) {
        if (Strings.isNullOrEmpty(value)) {
            return;
        }
        Preconditions.checkArgument(minLength <= value.length() && value.length() <= maxLength, String.format(
                "field [%s] allowed length is between [%s] and [%s], actual length is [%s]",
                fieldName, minLength, maxLength, value.length()));
    }

    public static void pattern(String value, String fieldName, String pattern) {
        if (Strings.isNullOrEmpty(value)) {
            return;
        }
        Preconditions.checkArgument(value.matches(pattern), String.format(
                "field [%s] value is invalid, should match pattern [%s]",
                fieldName, pattern));
    }

    public static String required(String value, String fieldName) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(value), String.format(
                "field [%s] is required",
                fieldName));
        return value;
    }

    public static <T> T required(T value, String fieldName) {
        Preconditions.checkArgument(value != null, String.format(
                "field [%s] is required",
                fieldName));
        return value;
    }

    public static void valid(boolean valid, String fieldName) {
        Preconditions.checkArgument(valid, String.format(
                "field [%s] value is invalid", fieldName));
    }
}
