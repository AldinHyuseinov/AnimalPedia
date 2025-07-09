package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.ConservationStatus;
import bg.softuni.animalpedia.models.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConservationStatusRepository extends JpaRepository<ConservationStatus, Long> {
    ConservationStatus getConservationStatusByStatus(Status status);
}
