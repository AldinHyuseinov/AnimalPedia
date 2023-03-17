package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.Animal;
import bg.softuni.animalpedia.models.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    Optional<Picture> findByAnimal(Animal animal);
}
