package bg.softuni.animalpedia.services;

import bg.softuni.animalpedia.models.dto.AddAnimalDTO;
import bg.softuni.animalpedia.models.dto.AnimalDTO;
import bg.softuni.animalpedia.models.dto.AnimalDetailsDTO;
import bg.softuni.animalpedia.models.entities.Animal;
import bg.softuni.animalpedia.repositories.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AnimalService {
    private final AnimalRepository animalRepository;

    private final PhylumRepository phylumRepository;

    private final AnimalClassRepository animalClassRepository;

    private final ConservationStatusRepository conservationStatusRepository;

    private final LocationRepository locationRepository;

    private final DietRepository dietRepository;

    private final SkinRepository skinRepository;

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    public void addAnimal(AddAnimalDTO addAnimalDTO, String username) {
        Animal animal = mapper.map(addAnimalDTO, Animal.class);
        animal.setPhylum(phylumRepository.getPhylumByType(addAnimalDTO.getPhylumType()));
        animal.setAnimalClass(animalClassRepository.getAnimalClassByAnimalClass(addAnimalDTO.getAnimalClass()));
        animal.setConservation(conservationStatusRepository.getConservationStatusByStatus(addAnimalDTO.getConservationStatus()));
        animal.setLocations(locationRepository.getAllByContinentIn(addAnimalDTO.getLocations()));
        animal.setDiet(dietRepository.getByType(addAnimalDTO.getDietType()));
        animal.setCreated(LocalDateTime.now());
        animal.setAddedBy(userRepository.findByUsername(username).orElse(null));

        if (addAnimalDTO.getSkinType() != null) {
            animal.setSkin(skinRepository.getByType(addAnimalDTO.getSkinType()));
        }
        animalRepository.save(animal);
    }

    public List<AnimalDTO> allAnimals() {
        return animalRepository.findAll().stream().map(animal -> mapper.map(animal, AnimalDTO.class)).collect(Collectors.toList());
    }

    public AnimalDetailsDTO animalByName(String specieName) {
        return animalRepository.findBySpecieName(specieName)
                .map(a -> {
                    AnimalDetailsDTO animalDetailsDTO = mapper.map(a, AnimalDetailsDTO.class);
                    animalDetailsDTO.getContinents().addAll(animalRepository.getContinentsBySpecie(animalDetailsDTO.getSpecieName()));
                    return animalDetailsDTO;
                })
                .orElseThrow(() -> new NoSuchElementException("No such animal found!"));
    }
}
