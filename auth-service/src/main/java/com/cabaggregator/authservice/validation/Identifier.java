package com.cabaggregator.authservice.validation;

import com.cabaggregator.authservice.core.constant.ValidationErrors;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IdentifierValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Identifier {
    String message() default ValidationErrors.INVALID_LOGIN_IDENTIFIER;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
