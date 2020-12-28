package com.nls.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class RequiredCollectionValidator implements ConstraintValidator<Required, Collection> {

    public void initialize(Required parameters) {

    }

    public boolean isValid(Collection object, ConstraintValidatorContext context) {
        return object != null && !object.isEmpty();
    }
}
