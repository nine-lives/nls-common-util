package com.nls.validation;

import com.google.common.base.Strings;
import com.nls.util.LocalDates;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class LocalDateFormatValidator implements ConstraintValidator<LocalDateFormat, String> {

    public void initialize(LocalDateFormat parameters) {
    }

    public boolean isValid(String object, ConstraintValidatorContext context) {

        if (Strings.isNullOrEmpty(object)) {
            return true;
        }

        return LocalDates.tryParse(object) != null;
    }
}
