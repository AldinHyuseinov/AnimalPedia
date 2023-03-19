package bg.softuni.animalpedia.utils.validations.validators;

import bg.softuni.animalpedia.models.AppUser;
import bg.softuni.animalpedia.repositories.UserRepository;
import bg.softuni.animalpedia.utils.validations.annotations.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final UserRepository userRepository;

    @Autowired
    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication == null) {
            return userRepository.findByEmail(value).isEmpty();
        }

        AppUser appUser = (AppUser) authentication.getPrincipal();

        if (appUser.getEmail().equals(value)) {
            return true;
        }

        return userRepository.findByEmail(value).isEmpty();
    }
}
