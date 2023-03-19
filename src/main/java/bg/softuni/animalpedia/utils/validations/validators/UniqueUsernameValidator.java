package bg.softuni.animalpedia.utils.validations.validators;

import bg.softuni.animalpedia.repositories.UserRepository;
import bg.softuni.animalpedia.utils.validations.annotations.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private final UserRepository userRepository;

    public UniqueUsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication == null) {
            return userRepository.findByUsername(value).isEmpty();
        }

        if (authentication.getName().equals(value)) {
            return true;
        }

        return userRepository.findByUsername(value).isEmpty();
    }
}
