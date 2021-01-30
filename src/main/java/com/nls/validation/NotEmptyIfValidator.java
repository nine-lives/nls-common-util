package com.nls.validation;

import com.google.common.base.Strings;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

class NotEmptyIfValidator implements ConstraintValidator<NotEmptyIf, Object> {

    private String fieldName;
    private String ifFieldName;
    private String ifFieldValue;

    public void initialize(NotEmptyIf parameters) {
        fieldName = parameters.fieldName();
        ifFieldName = parameters.ifFieldName();
        ifFieldValue = parameters.ifFieldValue();
    }

    public boolean isValid(Object object, ConstraintValidatorContext context) {

        if (object == null) {
            return true;
        }

        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
        Object ifValue = wrapper.getPropertyValue(ifFieldName);
        Object value = wrapper.getPropertyValue(fieldName);

        boolean valueIsEmpty = value == null
                || isEmptyString(value)
                || isEmptyCollection(value)
                || isEmptyArray(value);

        boolean valid = ifValue == null
                || !ifFieldValue.equals(ifValue.toString())
                || (ifFieldValue.equals(ifValue.toString()) && !valueIsEmpty);

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(fieldName)
                    .addConstraintViolation();
        }

        return valid;
    }

    private boolean isEmptyArray(Object value) {
        return value.getClass().isArray() && ((Object[]) value).length > 0;
    }

    private boolean isEmptyCollection(Object value) {
        return value instanceof Collection && ((Collection) value).isEmpty();
    }

    private boolean isEmptyString(Object value) {
        return value instanceof String && Strings.isNullOrEmpty(value.toString());
    }
}
