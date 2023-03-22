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

    // Uncomment below to run every 10 seconds for testing
    // @Scheduled(cron = "*/10 * * * * *")
    // Scheduled task to run every day at 8am
    @Scheduled(cron = "0 0 8 * * *", zone = "Europe/Sofia")
    public void animalOfTheDay() {
        animalOfTheDay = animalService.randomAnimal();

        Optional<Picture> picture = pictureService.animalPictureByName(animalOfTheDay.getSpecieName());
        picture.ifPresent(value -> animalOfTheDay.setUrl(value.getUrl()));

        LOGGER.info("Animal of the day is: {}", animalOfTheDay.getSpecieName());
    }

    // Expose the selected animal through a method
    public AnimalOfTheDayDTO getAnimalOfTheDay() {
        return animalOfTheDay;
    }
}

