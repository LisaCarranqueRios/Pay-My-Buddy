package com.payment.application.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class EmailValidatorTest {

    @InjectMocks
    EmailValidator emailValidator;
    ConstraintValidatorContext context;

    @Test
    public void testEmail() {
        assertTrue(emailValidator.isValid("jeanne@mail.com", context));
    }

}
