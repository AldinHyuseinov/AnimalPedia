package bg.softuni.animalpedia.models.dto;

import bg.softuni.animalpedia.models.enums.*;
import bg.softuni.animalpedia.models.enums.Class;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public abstract class AddEditAnimal {
    @NotNull(message = "Select the phylum!")
    private PhylumType phylumType;

    @NotNull(message = "Select the animal class!")
    private Class animalClass;

    @Size(min = 3, max = 40, message = "Order should be between 3 and 40 symbols!")
    private String animalOrder;

    @Size(min = 3, max = 40, message = "The family should be between 3 and 40 symbols!")
    private String animalFamily;

    @Size(min = 3, max = 40, message = "Genus should be between 3 and 40 symbols!")
    private String genus;

    @Size(min = 3, max = 50, message = "Scientific name should be between 3 and 50 symbols!")
    private String scientificName;

    @NotEmpty(message = "Select at least one continent!")
    private Set<Continent> locations;

    // Facts and characteristics
    @NotNull(message = "Select the animal conservation status!")
    private Status conservationStatus;

    @Size(min = 3, max = 100, message = "Habitat should be between 3 and 100 symbols!")
    private String habitat;

    @NotNull(message = "Select animal diet!")
    private DietType dietType;

    private SkinType skinType;

    @NotNull(message = "Select the average lifespan of the animal!")
    @Min(value = 0, message = "Lifespan cannot be negative!")
    private Integer lifespan;

    @Size(min = 5, message = "Write additional information about the animal that is at least 5 symbols long!")
    private String description;
}
