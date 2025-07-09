package bg.softuni.animalpedia.services;

import bg.softuni.animalpedia.models.AppUser;
import bg.softuni.animalpedia.models.entities.User;
import bg.softuni.animalpedia.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found!"));
    }

    private UserDetails map(User user) {
        return new AppUser(user.getUsername(), user.getPassword(), List.of(mapRole(user.getRole().getRole().name())),
                user.getFirstName(), user.getLastName(), user.getEmail(), user.getImageUrl());
    }

    private GrantedAuthority mapRole(String role) {
        return new SimpleGrantedAuthority("ROLE_" + role);
    }
}
