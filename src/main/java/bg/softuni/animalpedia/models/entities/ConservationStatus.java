package bg.softuni.animalpedia.models.entities;

import bg.softuni.animalpedia.models.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "conservation_statuses")
@NoArgsConstructor
@Getter
@Setter
public class ConservationStatus extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}
