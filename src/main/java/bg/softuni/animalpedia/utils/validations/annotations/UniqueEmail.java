package bg.softuni.animalpedia.utils.validations.annotations;

import bg.softuni.animalpedia.utils.validations.validators.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
    String message() default "Email must be unique.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
