package com.nls.validation;

import com.google.common.base.Strings;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class NotNullIfValidator implements ConstraintValidator<NotNullIf, Object> {

    private String fieldName;
    private String ifFieldName;
    private String ifFieldValue;
    private boolean negate;

    public void initialize(NotNullIf parameters) {
        fieldName = parameters.fieldName();
        ifFieldName = parameters.ifFieldName();
        ifFieldValue = parameters.ifFieldValue();
        negate = parameters.negate();
    }

    public boolean isValid(Object object, ConstraintValidatorContext context) {

        if (object == null) {
            return true;
        }

        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
        Object ifValue = wrapper.getPropertyValue(ifFieldName);
        Object value = wrapper.getPropertyValue(fieldName);

        boolean valueIsEmpty = value == null || value instanceof String && Strings.isNullOrEmpty(value.toString());
        boolean ifFieldValueMatches = !negate == (ifValue != null && ifFieldValue.equals(ifValue.toString()));
        boolean valid = !ifFieldValueMatches || !valueIsEmpty;

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(fieldName)
                    .addConstraintViolation();
        }

        return valid;
    }
}
