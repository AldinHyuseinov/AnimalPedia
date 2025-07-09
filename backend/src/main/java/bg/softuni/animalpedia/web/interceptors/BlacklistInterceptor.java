package bg.softuni.animalpedia.web.interceptors;

import bg.softuni.animalpedia.services.BanService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.util.Locale;
import java.util.Map;

@Component
public class BlacklistInterceptor implements HandlerInterceptor {
    private final BanService banService;

    private final ThymeleafViewResolver tlvr;

    public BlacklistInterceptor(BanService banService, ThymeleafViewResolver tlvr) {
        this.banService = banService;
        this.tlvr = tlvr;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();

            if (banService.isBanned(username)) {
                tlvr.addStaticVariable("bannedMessage", "You're account has been banned until: " + banService.getBanTime(username) + ". " + "Reason: " +
                        banService.bannedUser(username).getReason());
                View blockedView = tlvr.resolveViewName("blocked", Locale.getDefault());

                if (blockedView != null) {
                    blockedView.render(Map.of(), request, response);
                }
                securityContext.setAuthentication(null);

                return false;
            }
        }
        return true;
    }
}
