package bg.softuni.animalpedia.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendForgotPasswordEmail(String userEmail, String username, String code) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom("animalpedia@example.com");
            mimeMessageHelper.setTo(userEmail);

            mimeMessageHelper.setSubject("Your verification code");
            mimeMessageHelper.setText(generateEmailTextForForgottenPassword(username, code), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendWelcomeEmail(String userEmail, String username) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom("animalpedia@example.com");
            mimeMessageHelper.setTo(userEmail);

            mimeMessageHelper.setSubject("Welcome!");
            mimeMessageHelper.setText(generateEmailTextForWelcomeEmail(username), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateEmailTextForForgottenPassword(String username, String code) {
        Context ctx = new Context();
        ctx.setLocale(Locale.getDefault());
        ctx.setVariable("username", username);
        ctx.setVariable("code", code);

        return templateEngine.process("email/forgotten-password", ctx);
    }

    private String generateEmailTextForWelcomeEmail(String username) {
        Context ctx = new Context();
        ctx.setLocale(Locale.getDefault());
        ctx.setVariable("username", username);

        return templateEngine.process("email/welcome", ctx);
    }
}
