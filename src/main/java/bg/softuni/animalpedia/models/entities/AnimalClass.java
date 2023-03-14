package bg.softuni.animalpedia.models.entities;

import bg.softuni.animalpedia.models.enums.Class;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "animal_classes")
@NoArgsConstructor
@Getter
@Setter
public class AnimalClass extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Class animalClass;

    private String description;
}
