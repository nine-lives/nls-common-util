package com.nls.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public final class PhoneUtil {
    private static final PhoneNumberUtil INSTANCE = PhoneNumberUtil.getInstance();

    private PhoneUtil() {
    }

    public static boolean valid(String phoneRaw) {
        if (phoneRaw == null || phoneRaw.isEmpty()) {
            return false;
        }

        try {
            Phonenumber.PhoneNumber phone = INSTANCE.parse(phoneRaw, "GB");
            return INSTANCE.isValidNumber(phone);
        } catch (NumberParseException e) {
            return false;
        }
    }

    public static String normalise(String phoneRaw) {
        if (!valid(phoneRaw)) {
            return phoneRaw;
        }

        return prettify(phoneRaw).replaceAll(" ", "");
    }

    public static String prettify(String phoneRaw) {
        if (phoneRaw == null || phoneRaw.isEmpty()) {
            return phoneRaw;
        }

        try {
            Phonenumber.PhoneNumber phone = INSTANCE.parse(phoneRaw, "GB");
            return  phone.getCountryCode() == 44
                    ? INSTANCE.format(phone, PhoneNumberUtil.PhoneNumberFormat.NATIONAL)
                    : INSTANCE.format(phone, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        } catch (NumberParseException ignore) {
            return phoneRaw;
        }
    }
}
