package bg.softuni.animalpedia.utils.validations.validators;

import bg.softuni.animalpedia.repositories.UserRepository;
import bg.softuni.animalpedia.utils.validations.annotations.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
        boolean isValid;

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            isValid = userRepository.findByUsername(value).isEmpty();
        } else if (authentication.getName().equals(value)) {
            isValid = true;
        } else {
            isValid = userRepository.findByUsername(value).isEmpty();
        }
        return isValid;
    }
}
