package bg.softuni.animalpedia.services;

import bg.softuni.animalpedia.models.entities.AnimalClass;
import bg.softuni.animalpedia.models.enums.Class;
import bg.softuni.animalpedia.repositories.AnimalClassRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AnimalClassService {
    private final AnimalClassRepository animalClassRepository;

    @PostConstruct
    private void initAnimalClasses() {

        if (animalClassRepository.count() == 0) {

            for (Class animalClassConst : Class.values()) {
                AnimalClass animalClass = new AnimalClass();
                animalClass.setAnimalClass(animalClassConst);
                animalClass.setDescription(descriptionFor(animalClassConst));

                animalClassRepository.save(animalClass);
            }
        }
    }

    private String descriptionFor(Class animalClass) {
        StringBuilder sb = new StringBuilder();

        switch (animalClass) {
            case JAWLESS_FISH -> sb.append("Primitive jawless fish including lampreys, hagfishes, and extinct groups.");

            case BONY_FISH ->
                    sb.append("Includes saltwater and freshwater fish with bony skeletons like eels, anglerfish, " +
                            "clown fish, swordfish, and catfish, carp, trout, and salmonids.");

            case CARTILAGINOUS_FISH -> sb.append("Composed of fish with skeletons composed of cartilage. " +
                    "Includes two subclasses: Elasmobranchii (rays, skates, sawfish, and sharks); Holocephali (chimaeras–ghost sharks).");

            case AMPHIBIAN ->
                    sb.append("Four-limbed, ectothermic vertebrates, including frogs, toads, salamanders, and newts.");

            case REPTILE ->
                    sb.append("Vertebrates with dry skin and scales such as snakes, turtles, lizards, and crocodilians.");

            case BIRD ->
                    sb.append("Warm-blooded, egg-laying animals characterized by two wings, two legs, and feathers.");

            case MAMMAL ->
                    sb.append("Warm-blooded four-legged (or two-armed, two-legged) animals that breathe with lungs and birth live young.");
        }
        return sb.toString();
    }
}
