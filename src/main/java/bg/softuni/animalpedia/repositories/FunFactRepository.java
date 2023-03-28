package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.FunFact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FunFactRepository extends JpaRepository<FunFact, Long> {
    Set<FunFact> findAllByForAnimalSpecieName(String specieName);

    @Modifying
    void deleteByForAnimalSpecieName(String specieName);
}
