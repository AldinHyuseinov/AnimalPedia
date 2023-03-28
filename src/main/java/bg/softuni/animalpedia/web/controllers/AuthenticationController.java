package bg.softuni.animalpedia.web.controllers;

import bg.softuni.animalpedia.models.dto.ResetPasswordDTO;
import bg.softuni.animalpedia.models.entities.User;
import bg.softuni.animalpedia.repositories.UserRepository;
import bg.softuni.animalpedia.services.EmailService;
import bg.softuni.animalpedia.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AuthenticationController {
    private final UserService userService;

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
    public String emailPrompt(@RequestParam("email") String userEmail, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Optional<User> user = userService.userByEmail(userEmail);

        if (user.isEmpty()) {
            redirectAttributes.addFlashAttribute("invalidEmail", "Please enter a valid email!");

            return "redirect:/auth/forgot-password/email";
        }
        emailService.sendForgotPasswordEmail(userEmail, user.get().getUsername(), userService.getVerificationCodeForUserEmail(userEmail));

        request.getSession(true).setAttribute("email", userEmail);

        return "redirect:/auth/forgot-password/code";
    }

    @GetMapping("/forgot-password/code")
    public String codePrompt() {
        return "forgot-password-code";
    }

    @PostMapping("/forgot-password/code")
    public String codePrompt(@RequestParam("code") String verificationCode, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession httpSession = request.getSession(false);

        String sessionExpired = redirectIfSessionExpired(redirectAttributes, httpSession);

        if (sessionExpired != null) {
            return sessionExpired;
        }
        String email = (String) httpSession.getAttribute("email");
        User user = userService.userByEmail(email).get();
        String code = user.getVerificationCode();

        if (code == null) {
            redirectAttributes.addFlashAttribute("expiredCode", "Your code has expired or doesn't exist, enter your " +
                    "email again if you wish for a new code for password reset.");
            httpSession.removeAttribute("email");
            
            return "redirect:/auth/forgot-password/email";
        }

        if (!code.equals(verificationCode)) {
            redirectAttributes.addFlashAttribute("invalidCode", "The code you entered is invalid!");

            return "redirect:/auth/forgot-password/code";
        }
        return "redirect:/auth/forgot-password/reset-password";
    }

    @ModelAttribute("resetPasswordModel")
    public ResetPasswordDTO initPasswordReset() {
        return new ResetPasswordDTO();
    }

    @GetMapping("/forgot-password/reset-password")
    public String resetPassword() {
        return "reset-password";
    }

    @PostMapping("/forgot-password/reset-password")
    public String resetPassword(@Valid ResetPasswordDTO resetPasswordModel, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.resetPasswordModel", bindingResult);
            redirectAttributes.addFlashAttribute("resetPasswordModel", resetPasswordModel);

            return "redirect:/auth/forgot-password/reset-password";
        }
        HttpSession httpSession = request.getSession(false);

        String sessionExpired = redirectIfSessionExpired(redirectAttributes, httpSession);

        if (sessionExpired != null) {
            return sessionExpired;
        }

        userService.resetPassword((String) httpSession.getAttribute("email"), resetPasswordModel);
        httpSession.removeAttribute("email");

        return "redirect:/auth/login";
    }

    private String redirectIfSessionExpired(RedirectAttributes redirectAttributes, HttpSession httpSession) {
        if (httpSession == null || httpSession.getAttribute("email") == null) {
            redirectAttributes.addFlashAttribute("invalidEmail", "Enter your email again!");

            return "redirect:/auth/forgot-password/email";
        }
        return null;
    }
}
