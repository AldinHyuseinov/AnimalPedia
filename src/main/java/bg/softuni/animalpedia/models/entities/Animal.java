package bg.softuni.animalpedia.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "animals")
@Getter
@Setter
public class Animal extends BaseEntity {
    @ManyToOne(optional = false)
    private Phylum phylum;

    @ManyToOne(optional = false)
    private AnimalClass animalClass;

    @Column(nullable = false)
    private String animalOrder;

    @Column(nullable = false)
    private String animalFamily;

    @Column(nullable = false)
    private String genus;

    @Column(nullable = false)
    private String specieName;

    @Column(nullable = false)
    private String scientificName;

    @ManyToOne(optional = false)
    private ConservationStatus conservation;

    @ManyToMany
    private Set<Location> locations;

    @Column(nullable = false)
    private String habitat;

    @ManyToOne(optional = false)
    private Diet diet;

    @ManyToOne
    private Skin skin;

    private Integer lifespan;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime created;

    private LocalDateTime modified;

    private boolean verified;

    @ManyToOne
    private User addedBy;

    public Animal() {
        locations = new HashSet<>();
    }
}
