package bg.softuni.animalpedia.models.dto;

import bg.softuni.animalpedia.utils.validations.annotations.UniqueSpecieName;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AddAnimalDTO extends AddEditAnimal {
    @Size(min = 3, max = 40, message = "Specie name should be between 3 and 40 symbols!")
    @UniqueSpecieName
    private String specieName;
}
