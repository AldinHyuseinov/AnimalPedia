package bg.softuni.animalpedia.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String imageUrl;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String role;

    private LocalDateTime created;

    private LocalDateTime modified;

    private UserLinksDTO userLinks;
}
