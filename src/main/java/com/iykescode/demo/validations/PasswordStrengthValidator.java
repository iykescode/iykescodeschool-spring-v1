package com.iykescode.demo.validations;

import com.iykescode.demo.annotation.PasswordValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordValidator, String> {

    List<String> weakPasswords;

    @Override
    public void initialize(PasswordValidator passwordValidator) {
        weakPasswords = Arrays.asList(
                "12345678", "password", "qwerty",
                "11111111", "22222222", "33333333",
                "44444444", "55555555", "66666666",
                "77777777", "88888888", "99999999", "00000000");
    }

    @Override
    public boolean isValid(String passwordField, ConstraintValidatorContext cxt) {
        return passwordField != null && (!weakPasswords.contains(passwordField));
    }
}
