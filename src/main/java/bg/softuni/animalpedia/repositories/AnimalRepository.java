package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.Animal;
import bg.softuni.animalpedia.models.enums.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Optional<Animal> findBySpecieName(String specieName);

    @Query("SELECT l.continent FROM Animal a JOIN a.locations l WHERE a.specieName = :specieName")
    Set<Continent> getContinentsBySpecie(String specieName);
}
