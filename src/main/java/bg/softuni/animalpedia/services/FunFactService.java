package bg.softuni.animalpedia.services;

import bg.softuni.animalpedia.models.dto.FunFactDTO;
import bg.softuni.animalpedia.models.entities.FunFact;
import bg.softuni.animalpedia.repositories.AnimalRepository;
import bg.softuni.animalpedia.repositories.FunFactRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class FunFactService {
    private final FunFactRepository funFactRepository;

    private final AnimalRepository animalRepository;

    private final ModelMapper mapper;

    public void addFunFact(FunFactDTO funFactDTO) {
        FunFact funFact = mapper.map(funFactDTO, FunFact.class);
        funFact.setForAnimal(animalRepository.findBySpecieName(funFactDTO.getForAnimalSpecieName()).orElse(null));

        funFactRepository.save(funFact);
    }

    public String getRandomFunFactAbout(String specieName) {
        Random random = new Random();

        List<FunFact> funFacts = funFactRepository.findAllByForAnimalSpecieName(specieName);

        if (funFacts.isEmpty()) {
            throw new NoSuchElementException("Cannot find fun fact for the animal " + specieName + "!");
        }

        return funFacts.get(random.nextInt(0, funFacts.size())).getFact();
    }
}
