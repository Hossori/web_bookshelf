package com.hsr.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.springframework.beans.factory.annotation.Autowired;

import com.hsr.annotation.UnregisteredEmail.UnregisteredEmailValidator;
import com.hsr.domain.user.service.UserService;

/**
 * email must not be registered in users table.
 * @param email
 */
@Documented
@Constraint(validatedBy={UnregisteredEmailValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UnregisteredEmail {

    String message() default "{com.hsr.annotation.UnregisteredEmail.message}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface List {
        UnregisteredEmail[] value();
    }

    public class UnregisteredEmailValidator implements ConstraintValidator<UnregisteredEmail, String> {

        @Autowired
        private UserService userService;
        private String message;

        @Override
        public void initialize(UnregisteredEmail annotation) {
            this.message = annotation.message();
        }

        @Override
        public boolean isValid(String email, ConstraintValidatorContext context) {

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();

            return userService.getByEmailNotDeleted(email) == null;
        }

    }

}