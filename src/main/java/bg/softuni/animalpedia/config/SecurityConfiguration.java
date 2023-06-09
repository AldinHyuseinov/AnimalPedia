package bg.softuni.animalpedia.config;

import bg.softuni.animalpedia.models.enums.Role;
import bg.softuni.animalpedia.repositories.UserRepository;
import bg.softuni.animalpedia.services.UserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/error", "/about", "/", "/auth/**", "/api/users/register", "/animals/all", "/api/animals/all", "/api/animals/{specie-name}",
                        "/api/animals/search/{search-term}", "/animals/search",
                        "/animals/{specie-name}", "/api/user-management/**", "/api/fun-fact/{specie-name}",
                        "/users/profile/{username}", "/api/animals/class/{animal-class}", "/animals/class/{animal-class}").permitAll()
                .requestMatchers("/users/all").hasRole(Role.ADMIN.name())
                .requestMatchers("/api/animals/verify/{specie-name}", "/api/animals/unverify/{specie-name}").hasAnyRole(Role.ADMIN.name(), Role.MODERATOR.name())
                .requestMatchers("/api/users/delete").hasAnyRole(Role.USER.name(), Role.MODERATOR.name())
                .requestMatchers("/api/users/edit", "/users/edit", "/animals/add", "/api/animals/add",
                        "/api/animals/delete/{specie-name}", "/api/animals/edit", "/users/profile",
                        "/api/fun-fact/add", "/pictures/**").authenticated()
                .anyRequest().denyAll().and().formLogin().loginPage("/auth/login")
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/").failureForwardUrl("/auth/login-error").and()
                .logout().invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .logoutUrl("/users/logout").logoutSuccessUrl("/").and().securityContext()
                .securityContextRepository(securityContextRepository()).and().csrf()
                .ignoringRequestMatchers("/api/**");

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository());
    }
}
