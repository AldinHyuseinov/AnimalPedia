package bg.softuni.animalpedia.models.dto;

import bg.softuni.animalpedia.utils.validations.annotations.UniqueEmail;
import bg.softuni.animalpedia.utils.validations.annotations.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EditUserDTO {
    private String imageUrl;

    @Size(min = 2, max = 20, message = "Username should be between 2 and 20 symbols.")
    @UniqueUsername
    private String username;

    @Size(min = 2, max = 20, message = "First name should be between 2 and 20 symbols.")
    private String firstName;

    @Size(min = 2, max = 20, message = "Last name should be between 2 and 20 symbols.")
    private String lastName;

    @Email(regexp = ".+@.+", message = "Email must be valid!")
    @UniqueEmail
    private String email;
}
