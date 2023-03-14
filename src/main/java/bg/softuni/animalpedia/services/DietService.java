package bg.softuni.animalpedia.services;

import bg.softuni.animalpedia.models.entities.Diet;
import bg.softuni.animalpedia.models.enums.DietType;
import bg.softuni.animalpedia.repositories.DietRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class DietService {
    private final DietRepository dietRepository;

    @PostConstruct
    private void initDiets() {

        if (dietRepository.count() == 0) {
            Arrays.stream(DietType.values()).map(dietType -> {
                Diet diet = new Diet();
                diet.setDietType(dietType);
                return diet;
            }).forEach(dietRepository::save);
        }
    }
}
