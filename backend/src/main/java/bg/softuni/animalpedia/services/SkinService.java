package bg.softuni.animalpedia.services;

import bg.softuni.animalpedia.models.entities.Skin;
import bg.softuni.animalpedia.models.enums.SkinType;
import bg.softuni.animalpedia.repositories.SkinRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class SkinService {
    private final SkinRepository skinRepository;

    @PostConstruct
    private void initSkins() {

        if (skinRepository.count() == 0) {
            Arrays.stream(SkinType.values()).map(skinType -> {
                Skin skin = new Skin();
                skin.setType(skinType);
                return skin;
            }).forEach(skinRepository::save);
        }
    }
}
