package com.caveofprogramming.socialnetwork.validation;

import com.caveofprogramming.socialnetwork.model.SiteUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, SiteUser> {
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {

    }

    @Override
    public boolean isValid(SiteUser user, ConstraintValidatorContext c) {
        String plainPassword = user.getPlainPassword();
        String repeatPassword = user.getRepeatPassword();

        if(plainPassword == null || plainPassword.length() == 0){
            return true;
        }

        if(plainPassword == null || !plainPassword.equals(repeatPassword)){
            return false;
        }
        return true;
    }
}
