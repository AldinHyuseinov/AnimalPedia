package bg.softuni.animalpedia;

import bg.softuni.animalpedia.models.dto.AnimalOfTheDayDTO;
import bg.softuni.animalpedia.models.entities.Picture;
import bg.softuni.animalpedia.services.AnimalService;
import bg.softuni.animalpedia.services.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AnimalOfTheDayTask {

    private final AnimalService animalService;

    private final PictureService pictureService;

    private AnimalOfTheDayDTO animalOfTheDay;

    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalOfTheDayTask.class);

    public AnimalOfTheDayTask(AnimalService animalService, PictureService pictureService) {
        this.animalService = animalService;
        this.pictureService = pictureService;
    }

    @Scheduled(fixedRate = 86400000) // every 24 hours
    public void animalOfTheDay() {
        animalOfTheDay = animalService.randomAnimal();

        if (animalOfTheDay != null) {
            Optional<Picture> picture = pictureService.animalPictureByName(animalOfTheDay.getSpecieName());
            picture.ifPresent(value -> animalOfTheDay.setUrl(value.getUrl()));

            LOGGER.info("Animal of the day is: {}", animalOfTheDay.getSpecieName());
        }
    }

    // Expose the selected animal through a method
    public AnimalOfTheDayDTO getAnimalOfTheDay() {
        return animalOfTheDay;
    }
}

