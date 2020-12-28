package com.nls.validation;

import com.google.common.base.Strings;
import com.nls.util.VatUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VATValidator implements ConstraintValidator<VAT, String> {

    public void initialize(VAT parameters) {
    }

    public boolean isValid(String object, ConstraintValidatorContext context) {
        return Strings.isNullOrEmpty(object) || VatUtil.valid(object);
    }
}
