package com.nls.validation;

import com.nls.util.BacsUtil;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class BankDetailsValidator implements ConstraintValidator<BankDetails, Object> {

    private String sortCodeField;
    private String accountNumberField;

    public void initialize(BankDetails parameters) {
        sortCodeField = parameters.sortCode();
        accountNumberField = parameters.accountNumber();
    }

    public boolean isValid(Object object, ConstraintValidatorContext context) {
        if (object == null) {
            return true;
        }

        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
        String sortCode = (String) wrapper.getPropertyValue(sortCodeField);
        String accountNumber = (String) wrapper.getPropertyValue(accountNumberField);

        if (!BacsUtil.validOrEmpty(sortCode, accountNumber)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(sortCodeField)
                    .addConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(accountNumberField)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
