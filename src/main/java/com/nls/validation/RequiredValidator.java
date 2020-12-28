package com.nls.validation;

import com.google.common.base.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RequiredValidator implements ConstraintValidator<Required, String> {

    private boolean trim;

    public void initialize(Required parameters) {
        trim = parameters.trim();
    }

    public boolean isValid(String object, ConstraintValidatorContext context) {
        return trim ? !Strings.isNullOrEmpty(Strings.nullToEmpty(object).trim()) : !Strings.isNullOrEmpty(object);
    }
}
