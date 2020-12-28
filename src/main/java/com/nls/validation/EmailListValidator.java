package com.nls.validation;

import com.nls.util.Emails;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class EmailListValidator implements ConstraintValidator<EmailList, String> {

    private final EmailValidator emailValidator = new EmailValidator();

    public void initialize(EmailList parameters) {
    }

    public boolean isValid(String object, ConstraintValidatorContext context) {
        List<String> emails = Emails.asList(object);
        for (String email : emails) {
            if (!emailValidator.isValid(email, context)) {
                return false;
            }
        }
        return true;
    }
}
