package bg.softuni.animalpedia.models.dto;

import bg.softuni.animalpedia.models.enums.*;
import bg.softuni.animalpedia.models.enums.Class;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AnimalDetailsDTO {
    private String url;

    private String specieName;

    private PhylumType phylumType;

    private Class animalClass;

    private String animalOrder;

    private String animalFamily;

    private String genus;

    private String scientificName;

    private Set<Continent> continents;

    private Status conservationStatus;

    private String habitat;

    private DietType dietType;

    private SkinType skinType;

    private Integer lifespan;

    private String description;

    public AnimalDetailsDTO() {
        continents = new HashSet<>();
    }
}
