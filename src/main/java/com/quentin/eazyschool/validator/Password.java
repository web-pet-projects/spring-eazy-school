package com.quentin.eazyschool.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "{validator.password.weak}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
