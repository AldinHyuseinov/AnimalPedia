package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.AnimalClass;
import bg.softuni.animalpedia.models.enums.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalClassRepository extends JpaRepository<AnimalClass, Long> {
    AnimalClass getAnimalClassByAnimalClass(Class animaClass);
}
