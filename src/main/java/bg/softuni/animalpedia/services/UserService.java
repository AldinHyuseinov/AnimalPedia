package bg.softuni.animalpedia.services;

import bg.softuni.animalpedia.models.dto.RegisterUserDTO;
import bg.softuni.animalpedia.models.entities.User;
import bg.softuni.animalpedia.models.enums.Role;
import bg.softuni.animalpedia.repositories.UserRepository;
import bg.softuni.animalpedia.repositories.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserService {
    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    private final ModelMapper mapper;

    public void registerUser(RegisterUserDTO registerUserDTO, Consumer<Authentication> loginProcessor) {
        User user = mapper.map(registerUserDTO, User.class);
        user.setCreated(LocalDateTime.now());
        user.setRole(userRoleRepository.findByRole(Role.USER));
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));

        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(registerUserDTO.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());

        loginProcessor.accept(authentication);
    }
}
