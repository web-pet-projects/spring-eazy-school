package com.quentin.eazyschool.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String first;
    private String second;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.first = constraintAnnotation.first();
        this.second = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            BeanWrapper beanWrapper = new BeanWrapperImpl(value);
            Object firstValue = beanWrapper.getPropertyValue(first);
            Object secondValue = beanWrapper.getPropertyValue(second);
            return Objects.equals(firstValue, secondValue);

        } catch (Exception e) {
            return false;
        }
    }
}
