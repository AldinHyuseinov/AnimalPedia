package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.Phylum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhylumRepository extends JpaRepository<Phylum, Long> {
}
