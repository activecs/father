package com.epam.env.father.service.validation.constrains;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.epam.env.father.service.validation.NotReserved;

public class NotReservedConstraint implements ConstraintValidator<NotReserved, String> {

    @Override
    public void initialize(NotReserved constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return true;
    }
}
