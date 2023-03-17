package bg.softuni.animalpedia.utils.validations.annotations;

import bg.softuni.animalpedia.utils.validations.validators.UniqueSpecieNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueSpecieNameValidator.class)
public @interface UniqueSpecieName {
    String message() default "Specie name already exists!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
