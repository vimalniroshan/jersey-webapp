package com.vml.jersey.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Constraint(validatedBy = Email.EmailValidator.class)
public @interface Email {
    String message() default "{com.example.validation.constraints.email}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class EmailValidator implements ConstraintValidator<Email, String> {
        @Override
        public void initialize(final Email constraintAnnotation) {
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext context) {
            return value != null && !value.matches("[a-zA-Z0-9.-_$]+@[a-zA-Z0-9]\\.[a-zA-Z0-9]{1,5}");
        }
    }
}
