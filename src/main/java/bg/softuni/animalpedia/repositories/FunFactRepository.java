package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.FunFact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunFactRepository extends JpaRepository<FunFact, Long> {
}
