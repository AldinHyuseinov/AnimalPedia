package bg.softuni.animalpedia.models.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FunFactDTO {
    private String forAnimalSpecieName;

    @Size(min = 5, message = "Fact should be at least 5 characters long!")
    private String fact;
}
