package bg.softuni.animalpedia.web.controllers;

import bg.softuni.animalpedia.models.entities.User;
import bg.softuni.animalpedia.repositories.UserRepository;
import bg.softuni.animalpedia.services.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AuthenticationController {
    private final UserRepository userRepository;

    private final EmailService emailService;

    @GetMapping("/login")
    public String login() {
        return "auth-login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
                                RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("bad_credentials", true);

        return "redirect:/auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/forgot-password/email")
    public String emailPrompt() {
        return "forgot-password-email";
    }

    @PostMapping("/forgot-password/email")
    public String emailPrompt(@RequestParam("email") String userEmail, RedirectAttributes redirectAttributes) {
        Optional<User> user = userRepository.findByEmail(userEmail);

        if (user.isEmpty()) {
            redirectAttributes.addFlashAttribute("invalidEmail", "Please enter a valid email!");

            return "redirect:/auth/forgot-password/email";
        }
        emailService.sendForgotPasswordEmail(userEmail, user.get().getUsername(), generateRandomCode());

        //TODO: to continue the process
        return "redirect:/";
    }

    private String generateRandomCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
