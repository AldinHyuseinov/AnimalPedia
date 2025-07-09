package bg.softuni.animalpedia.models;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AppUser extends User {
    private final String firstName;

    private final String lastName;

    private final String email;

    private final String imageUrl;


    public AppUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String firstName,
                   String lastName, String email, String imageUrl) {
        super(username, password, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imageUrl = imageUrl;
    }
}
