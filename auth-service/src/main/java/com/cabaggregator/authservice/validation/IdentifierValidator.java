package com.cabaggregator.authservice.validation;

import com.cabaggregator.authservice.core.constant.ValidationRegex;
import com.cabaggregator.authservice.util.MessageBuilder;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdentifierValidator implements ConstraintValidator<Identifier, String> {
    private final MessageBuilder messageBuilder;
    private String messageTemplate;

    @Override
    public void initialize(Identifier constraintAnnotation) {
        this.messageTemplate = constraintAnnotation.message();
        this.messageTemplate = messageTemplate.substring(1, messageTemplate.length() - 1);
    }

    @Override
    public boolean isValid(String identifier, ConstraintValidatorContext context) {
        boolean isNotEmpty = identifier != null && !identifier.isEmpty();
        boolean isPhoneValid = isNotEmpty && identifier.matches(ValidationRegex.PHONE_BELARUS_FORMAT);
        boolean isEmailValid = isNotEmpty && identifier.matches(ValidationRegex.EMAIL);
        boolean isValid = isPhoneValid || isEmailValid;

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            messageBuilder.buildLocalizedMessage(
                                    messageTemplate,
                                    null))
                    .addConstraintViolation();
        }

        return isValid;
    }
}
