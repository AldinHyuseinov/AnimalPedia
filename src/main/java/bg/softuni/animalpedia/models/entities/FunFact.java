package bg.softuni.animalpedia.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fun_facts")
@NoArgsConstructor
@Getter
@Setter
public class FunFact extends BaseEntity {
    @Column(nullable = false)
    private String fact;

    @ManyToOne(optional = false)
    private Animal forAnimal;
}
