package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.Animal;
import bg.softuni.animalpedia.models.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    Optional<Picture> findFirstByAnimal(Animal animal);

    Picture findByUrl(String url);

    List<Picture> findAllByAnimalSpecieName(String specieName);

    @Modifying
    void deleteByAnimal(Animal animal);

    @Modifying
    @Transactional
    void deleteByAnimalSpecieName(String specieName);
}
