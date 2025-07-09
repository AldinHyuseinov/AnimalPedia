package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.Diet;
import bg.softuni.animalpedia.models.enums.DietType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietRepository extends JpaRepository<Diet, Long> {
    Diet getByType(DietType type);
}
