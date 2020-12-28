package com.nls.validation;

import com.nls.util.Emails;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class WhitelistValidator implements ConstraintValidator<Whitelist, String> {

    public void initialize(Whitelist parameters) {
    }

    public boolean isValid(String object, ConstraintValidatorContext context) {
        List<String> emails = Emails.asList(object);
        for (String email : emails) {
            if (!Emails.validDomainOrEmail(email)) {
                return false;
            }
        }
        return true;
    }
}
