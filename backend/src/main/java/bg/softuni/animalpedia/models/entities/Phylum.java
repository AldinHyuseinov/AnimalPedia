package bg.softuni.animalpedia.models.entities;

import bg.softuni.animalpedia.models.enums.PhylumType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "phyla")
@NoArgsConstructor
@Getter
@Setter
public class Phylum extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhylumType type;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
}
