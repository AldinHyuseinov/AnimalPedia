package bg.softuni.animalpedia.utils.validations.annotations;

import bg.softuni.animalpedia.utils.validations.validators.FieldMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = FieldMatchValidator.class)
public @interface FieldMatch {

    String first();

    String second();

    String message() default "Passwords should match.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
