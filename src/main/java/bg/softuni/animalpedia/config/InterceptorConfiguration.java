package bg.softuni.animalpedia.config;

import bg.softuni.animalpedia.web.interceptors.BlacklistInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    private final BlacklistInterceptor blacklistInterceptor;

    public InterceptorConfiguration(BlacklistInterceptor blacklistInterceptor) {
        this.blacklistInterceptor = blacklistInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(blacklistInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
