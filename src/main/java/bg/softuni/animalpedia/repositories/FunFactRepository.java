package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.FunFact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FunFactRepository extends JpaRepository<FunFact, Long> {
    List<FunFact> findAllByForAnimalSpecieName(String specieName);

    @Modifying
    void deleteByForAnimalSpecieName(String specieName);

    @Modifying
    @Transactional
    void deleteByFromUserUsername(String username);
}
