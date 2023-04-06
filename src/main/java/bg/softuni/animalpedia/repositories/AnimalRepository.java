package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.Animal;
import bg.softuni.animalpedia.models.enums.Class;
import bg.softuni.animalpedia.models.enums.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>, JpaSpecificationExecutor<Animal> {
    Optional<Animal> findBySpecieName(String specieName);

    @Query("SELECT l.continent FROM Animal a JOIN a.locations l WHERE a.specieName = :specieName")
    Set<Continent> getContinentsBySpecie(String specieName);

    Set<Animal> findAllByAddedByUsername(String username);

    @Query("SELECT a FROM Animal AS a JOIN a.animalClass AS ac WHERE ac.animalClass = :animalClass Order by a.specieName")
    List<Animal> findAllByClass(Class animalClass);

    @Query("SELECT a FROM Animal AS a JOIN a.animalClass AS ac WHERE ac.animalClass in (bg.softuni.animalpedia.models.enums.Class.BONY_FISH, " +
            "bg.softuni.animalpedia.models.enums.Class.CARTILAGINOUS_FISH, bg.softuni.animalpedia.models.enums.Class.JAWLESS_FISH) ORDER BY a.specieName")
    List<Animal> findAllFish();
}
