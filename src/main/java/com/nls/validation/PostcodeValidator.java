package com.nls.validation;

import com.nls.util.PostcodeUtil;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostcodeValidator implements ConstraintValidator<Postcode, Object> {
    private String postcodeField;
    private String countryField;
    private String countryValue;

    public void initialize(Postcode parameters) {
        this.countryField = parameters.countryField();
        this.countryValue = parameters.countryValue();
        this.postcodeField = parameters.postcodeField();
    }

    public boolean isValid(Object object, ConstraintValidatorContext context) {
        if (object == null) {
            return true;
        }

        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
        if (countryField != null && !countryField.isEmpty()) {
            String country = String.valueOf(wrapper.getPropertyValue(countryField));
            if (country != null && !country.equals(countryValue)) {
                return true;
            }
        }

        String postcode = (String) wrapper.getPropertyValue(postcodeField);
        if (postcode == null || postcode.isEmpty()) {
            return true;
        }

        if (!PostcodeUtil.valid(postcode)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(postcodeField)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
