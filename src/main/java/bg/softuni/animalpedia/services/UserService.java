package bg.softuni.animalpedia.services;

import bg.softuni.animalpedia.models.dto.EditUserDTO;
import bg.softuni.animalpedia.models.dto.RegisterUserDTO;
import bg.softuni.animalpedia.models.dto.UserDTO;
import bg.softuni.animalpedia.models.entities.User;
import bg.softuni.animalpedia.models.enums.Role;
import bg.softuni.animalpedia.repositories.UserRepository;
import bg.softuni.animalpedia.repositories.UserRoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

        login(loginProcessor, userDetails);
    }

    public void editUser(EditUserDTO editUserDTO, String username, Consumer<Authentication> loginProcessor) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (!editUserDTO.getImageUrl().equals("")) {
            user.setImageUrl(editUserDTO.getImageUrl());
        }
        user.setUsername(editUserDTO.getUsername());
        user.setFirstName(editUserDTO.getFirstName());
        user.setLastName(editUserDTO.getLastName());
        user.setEmail(editUserDTO.getEmail());
        user.setModified(LocalDateTime.now());

        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(editUserDTO.getUsername());

        login(loginProcessor, userDetails);
    }

    public List<UserDTO> allUsers() {
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user : userRepository.findAll()) {

            if (user.getRole().getRole().compareTo(Role.ADMIN) == 0) {
                continue;
            }
            UserDTO userDTO = mapper.map(user, UserDTO.class);
            userDTO.setRole(user.getRole().getRole().name());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    public void promoteUser(String username) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user.getRole().getRole().compareTo(Role.USER) == 0) {
            user.setRole(userRoleRepository.findByRole(Role.MODERATOR));
            user.setModified(LocalDateTime.now());
        } else {
            throw new UnsupportedOperationException("Can't promote more than role Moderator!");
        }
        userRepository.save(user);
    }

    public void demoteUser(String username) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user.getRole().getRole().compareTo(Role.MODERATOR) == 0) {
            user.setRole(userRoleRepository.findByRole(Role.USER));
            user.setModified(LocalDateTime.now());
        } else {
            throw new UnsupportedOperationException("Can't demote more than role User!");
        }
        userRepository.save(user);
    }

    private void login(Consumer<Authentication> loginProcessor, UserDetails userDetails) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());
        loginProcessor.accept(authentication);
    }
}
