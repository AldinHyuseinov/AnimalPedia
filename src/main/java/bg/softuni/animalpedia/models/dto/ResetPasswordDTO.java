package bg.softuni.animalpedia.models.dto;

import bg.softuni.animalpedia.utils.validations.annotations.FieldMatch;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@FieldMatch(first = "password", second = "confirmPassword")
public class ResetPasswordDTO {
    @Size(min = 5, message = "Password should be at least 5 symbols.")
    private String password;

    @Size(min = 5, message = "Confirm password should be at least 5 symbols.")
    private String confirmPassword;
}
