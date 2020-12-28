package com.nls.util;

import com.google.common.base.Strings;
import com.nls.bank.BankAccountValidator;
import com.nls.bank.SortCode;

import java.io.IOException;

public final class BacsUtil {

    private static BankAccountValidator validator;

    static {
        try {
            validator = new BankAccountValidator();
        } catch (IOException ignore) {
        }
    }

    private BacsUtil() {

    }
    public static boolean validOrEmpty(String sortCode, String accountNumber) {
        if (normalise(sortCode).isEmpty() && normalise(accountNumber).isEmpty()) {
            return true;
        }
        return valid(sortCode, accountNumber);
    }

    public static boolean valid(String sortCode, String accountNumber) {
        try {
            return validator.valid(sortCode, accountNumber);
        } catch (IllegalArgumentException | NullPointerException ignore) {
            return false;
        }
    }

    public static String prettifySortCode(String sortCode) {
        try {
            return new SortCode(sortCode).toString();
        } catch (IllegalArgumentException | NullPointerException ignore) {
            return "";
        }
    }

    public static String normalise(String s) {
        return Strings.nullToEmpty(s).replaceAll("[\\D]", "");
    }
}
