package bg.softuni.animalpedia.services;

import bg.softuni.animalpedia.models.dto.FunFactDTO;
import bg.softuni.animalpedia.models.entities.FunFact;
import bg.softuni.animalpedia.repositories.AnimalRepository;
import bg.softuni.animalpedia.repositories.FunFactRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

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

    public Set<String> getFactsByAnimalName(String specieName) {
        return funFactRepository.findAllByForAnimalSpecieName(specieName).stream().map(FunFact::getFact).collect(Collectors.toSet());
    }
}
