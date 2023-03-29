package bg.softuni.animalpedia.config;

import bg.softuni.animalpedia.models.enums.Role;
import bg.softuni.animalpedia.repositories.UserRepository;
import bg.softuni.animalpedia.services.UserDetailsServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableMethodSecurity
public class BeanConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/", "/auth/**", "/api/users/register", "/animals/all", "/api/animals/all", "/api/animals/{specie-name}",
                        "/animals/{specie-name}", "/api/admin/**", "/api/fun-fact/{specie-name}").permitAll()
                .requestMatchers("/users/all").hasRole(Role.ADMIN.name())
                .requestMatchers("/api/users/edit", "/users/edit", "/animals/add", "/api/animals/add",
                        "/api/animals/delete/{specie-name}", "/api/users/delete", "/users/profile", "/api/fun-fact/add", "/pictures/upload/{specie-name}")
                .hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.MODERATOR.name())
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

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate create(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}
