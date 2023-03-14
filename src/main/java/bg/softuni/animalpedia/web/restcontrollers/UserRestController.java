package bg.softuni.animalpedia.web.restcontrollers;

import bg.softuni.animalpedia.models.dto.RegisterUserDTO;
import bg.softuni.animalpedia.services.UserService;
import bg.softuni.animalpedia.utils.exceptions.UserRegistrationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    private final SecurityContextRepository securityContextRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

    public UserRestController(UserService userService, SecurityContextRepository securityContextRepository) {
        this.userService = userService;
        this.securityContextRepository = securityContextRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid RegisterUserDTO registerUserDTO, BindingResult bindingResult,
                                             HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        if (bindingResult.hasErrors()) {
            Map<String, String> fieldAndMessage = new HashMap<>();
            bindingResult.getFieldErrors().forEach(fieldError -> fieldAndMessage.put(fieldError.getField(),
                    fieldError.getDefaultMessage()));
            throw new UserRegistrationException(fieldAndMessage);
        }

        userService.registerUser(registerUserDTO, auth -> {
            SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();

            SecurityContext context = strategy.createEmptyContext();
            context.setAuthentication(auth);

            strategy.setContext(context);

            securityContextRepository.saveContext(context, httpServletRequest, httpServletResponse);
        });
        LOGGER.info("Registered and logged in user with username: {}", registerUserDTO.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<Map<String, String>> onRegistrationError(UserRegistrationException ure) {
        return ResponseEntity.badRequest().body(ure.getFieldAndMessage());
    }
}
