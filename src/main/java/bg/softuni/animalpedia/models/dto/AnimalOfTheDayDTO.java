package bg.softuni.animalpedia.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AnimalOfTheDayDTO {
    private String specieName;

    private String url;

    private String description;
}
