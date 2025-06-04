package com.quentin.eazyschool.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    List<String> weakPasswords;
    @Override
    public void initialize(Password constraintAnnotation) {
        weakPasswords = Arrays.asList("12345", "password", "qwerty");
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password != null && !weakPasswords.contains(password);
    }
}
