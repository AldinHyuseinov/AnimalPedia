package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.ConservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConservationStatusRepository extends JpaRepository<ConservationStatus, Long> {
}
