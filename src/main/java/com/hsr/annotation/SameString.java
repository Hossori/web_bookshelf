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

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.hsr.annotation.SameString.SameStringValidator;

/**
 * field1 and field2 must have equal value.
 * @param field1
 * @param field2
 */
@Documented
@Constraint(validatedBy={SameStringValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SameString {

    String field1();
    String field2();
    String message() default "{com.hsr.annotation.SameString.message}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface List {
        SameString[] value();
    }

    public class SameStringValidator implements ConstraintValidator<SameString, Object> {

        private String field1;
        private String field2;
        private String message;

        @Override
        public void initialize(SameString annotation) {
            this.field1 = annotation.field1();
            this.field2 = annotation.field2();
            this.message = annotation.message();
        }

        @Override
        public boolean isValid(Object target, ConstraintValidatorContext context) {

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(field2)
                    .addConstraintViolation();

            BeanWrapper beanWrapper = new BeanWrapperImpl(target);
            String value1 = (String) beanWrapper.getPropertyValue(field1);
            String value2 = (String) beanWrapper.getPropertyValue(field2);

            return value1.equals(value2);
        }

    }

}