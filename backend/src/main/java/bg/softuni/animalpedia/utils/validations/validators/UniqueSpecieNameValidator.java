package bg.softuni.animalpedia.utils.validations.validators;

import bg.softuni.animalpedia.repositories.AnimalRepository;
import bg.softuni.animalpedia.utils.validations.annotations.UniqueSpecieName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueSpecieNameValidator implements ConstraintValidator<UniqueSpecieName, String> {
    private final AnimalRepository animalRepository;

    public UniqueSpecieNameValidator(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return animalRepository.findBySpecieName(value).isEmpty();
    }
}
