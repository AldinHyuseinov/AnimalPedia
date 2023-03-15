package bg.softuni.animalpedia.models.entities;

import bg.softuni.animalpedia.models.enums.DietType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "diets")
@NoArgsConstructor
@Getter
@Setter
public class Diet extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DietType type;
}
