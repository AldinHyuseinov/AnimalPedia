package bg.softuni.animalpedia.models.entities;

import bg.softuni.animalpedia.models.enums.Continent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "locations")
@NoArgsConstructor
@Getter
@Setter
public class Location extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Continent continent;
}
