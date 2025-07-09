package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.Skin;
import bg.softuni.animalpedia.models.enums.SkinType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkinRepository extends JpaRepository<Skin, Long> {
    Skin getByType(SkinType type);
}
