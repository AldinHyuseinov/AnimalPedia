package bg.softuni.animalpedia.models.entities;

import bg.softuni.animalpedia.models.enums.SkinType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "animal_skins")
@NoArgsConstructor
@Getter
@Setter
public class Skin extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private SkinType type;
}
