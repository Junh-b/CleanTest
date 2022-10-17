package net.junhabaek.tddpractice.common.validation.constraint;

import net.junhabaek.tddpractice.common.validation.validator.DistinguishableNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DistinguishableNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DistinguishableName {
    String message() default "Indistinguishable Name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
