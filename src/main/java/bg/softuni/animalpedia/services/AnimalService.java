package bg.softuni.animalpedia.services;

import bg.softuni.animalpedia.models.dto.*;
import bg.softuni.animalpedia.models.entities.Animal;
import bg.softuni.animalpedia.models.entities.Location;
import bg.softuni.animalpedia.repositories.*;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static bg.softuni.animalpedia.services.UserService.userAuthorizationCheck;

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

    private final PictureRepository pictureRepository;

    private final FunFactRepository funFactRepository;

    private final ModelMapper mapper;

    public void addAnimal(AddAnimalDTO addAnimalDTO, String username) {
        Animal animal = mapper.map(addAnimalDTO, Animal.class);
        setValues(addAnimalDTO, animal);
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

    public AnimalOfTheDayDTO randomAnimal() {
        List<Animal> animals = animalRepository.findAll();

        Random random = new Random();
        int index = random.nextInt(animals.size());

        return mapper.map(animals.get(index), AnimalOfTheDayDTO.class);
    }

    @Transactional
    public void deleteAnimal(String specieName) {
        Optional<Animal> animalOpt = animalRepository.findBySpecieName(specieName);

        if (animalOpt.isEmpty()) {
            throw new NoSuchElementException("Animal to delete doesn't exist!");
        }
        Animal animal = animalOpt.get();

        if (!userAuthorizationCheck(animal.getAddedBy().getUsername())) {
            throw new UnsupportedOperationException("Not allowed to delete!");
        }

        Set<Location> locations = animal.getLocations();
        locations.clear();
        animal.setAddedBy(null);
        pictureRepository.deleteByAnimal(animal);
        funFactRepository.deleteByForAnimalSpecieName(specieName);

        animalRepository.delete(animal);
    }

    public Set<Animal> allAnimalsByUser(String username) {
        return animalRepository.findAllByAddedByUsername(username);
    }

    public void editAnimal(EditAnimalDTO editAnimalDTO) {
        Animal animal = animalRepository.findBySpecieName(editAnimalDTO.getSpecieName()).orElse(null);
        setValues(editAnimalDTO, animal);
        animal.setGenus(editAnimalDTO.getGenus());
        animal.setScientificName(editAnimalDTO.getScientificName());
        animal.setHabitat(editAnimalDTO.getHabitat());
        animal.setLifespan(editAnimalDTO.getLifespan());
        animal.setDescription(editAnimalDTO.getDescription());
        animal.setModified(LocalDateTime.now());

        if (editAnimalDTO.getSkinType() != null) {
            animal.setSkin(skinRepository.getByType(editAnimalDTO.getSkinType()));
        }
        animalRepository.save(animal);
    }

    public void verifyAnimal(String specieName) {
        Animal animal = animalRepository.findBySpecieName(specieName).orElse(null);
        animal.setVerified(true);
        animal.setModified(LocalDateTime.now());

        animalRepository.save(animal);
    }

    public void unverifyAnimal(String specieName) {
        Animal animal = animalRepository.findBySpecieName(specieName).orElse(null);
        animal.setVerified(false);
        animal.setModified(LocalDateTime.now());

        animalRepository.save(animal);
    }

    private <T extends AddEditAnimal> void setValues(T addEditAnimalDTO, Animal animal) {
        animal.setPhylum(phylumRepository.getPhylumByType(addEditAnimalDTO.getPhylumType()));
        animal.setAnimalClass(animalClassRepository.getAnimalClassByAnimalClass(addEditAnimalDTO.getAnimalClass()));
        animal.setConservation(conservationStatusRepository.getConservationStatusByStatus(addEditAnimalDTO.getConservationStatus()));
        animal.setLocations(locationRepository.getAllByContinentIn(addEditAnimalDTO.getLocations()));
        animal.setDiet(dietRepository.getByType(addEditAnimalDTO.getDietType()));
    }
}
