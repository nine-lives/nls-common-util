package com.nls.validation;

import com.google.common.base.Strings;
import com.nls.util.PhoneUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TelephoneValidator implements ConstraintValidator<Telephone, String> {

    public void initialize(Telephone parameters) {
    }

    public boolean isValid(String object, ConstraintValidatorContext context) {
        return Strings.isNullOrEmpty(object) || PhoneUtil.valid(object);
    }
}
