package bg.softuni.animalpedia.services;

import bg.softuni.animalpedia.models.dto.AddPictureDTO;
import bg.softuni.animalpedia.models.dto.PictureDTO;
import bg.softuni.animalpedia.models.entities.Picture;
import bg.softuni.animalpedia.repositories.AnimalRepository;
import bg.softuni.animalpedia.repositories.PictureRepository;
import bg.softuni.animalpedia.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class PictureService {
    private final PictureRepository pictureRepository;

    private final AnimalRepository animalRepository;

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    @Transactional
    public void addPicture(AddPictureDTO pictureDTO, String username) {
        Picture picture = mapper.map(pictureDTO, Picture.class);
        picture.setId(null);
        picture.setAnimal(animalRepository.findBySpecieName(pictureDTO.getSpecieName()).orElse(null));
        picture.setAuthor(userRepository.findByUsername(username).orElse(null));

        Picture existingPicture = pictureRepository.findByUrl(picture.getUrl());

        if (existingPicture != null) {
            existingPicture.setTitle(picture.getTitle());
            existingPicture.setAnimal(picture.getAnimal());
            existingPicture.setAuthor(picture.getAuthor());
            pictureRepository.save(existingPicture);
        } else {
            pictureRepository.save(picture);
        }
    }

    public Optional<Picture> animalPictureByName(String specieName) {
        return pictureRepository.findFirstByAnimal(animalRepository.findBySpecieName(specieName).orElse(null));
    }

    public List<PictureDTO> allAnimalPicturesByName(String specieName) {
        return pictureRepository.findAllByAnimalSpecieName(specieName).stream()
                .map(picture -> mapper.map(picture, PictureDTO.class)).collect(Collectors.toList());
    }
}
