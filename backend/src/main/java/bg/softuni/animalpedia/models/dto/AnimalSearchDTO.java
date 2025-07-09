package bg.softuni.animalpedia.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AnimalSearchDTO {
    private String specieName;

    private String scientificName;

    private String animalOrder;

    private String animalFamily;

    private String genus;
}
