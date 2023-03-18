package bg.softuni.animalpedia.web.restcontrollers;

import bg.softuni.animalpedia.models.AppUser;
import bg.softuni.animalpedia.models.dto.AddAnimalDTO;
import bg.softuni.animalpedia.models.dto.AnimalDTO;
import bg.softuni.animalpedia.models.dto.AnimalDetailsDTO;
import bg.softuni.animalpedia.models.entities.Picture;
import bg.softuni.animalpedia.services.AnimalService;
import bg.softuni.animalpedia.services.PictureService;
import bg.softuni.animalpedia.utils.exceptions.FormException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/animals")
public class AnimalRestController {
    private final AnimalService animalService;

    private final PictureService pictureService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalRestController.class);

    public AnimalRestController(AnimalService animalService, PictureService pictureService) {
        this.animalService = animalService;
        this.pictureService = pictureService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAnimal(@RequestBody @Valid AddAnimalDTO addAnimalDTO, BindingResult bindingResult,
                                       @AuthenticationPrincipal AppUser appUser) {

        if (bindingResult.hasErrors()) {
            Map<String, String> fieldAndMessage = new HashMap<>();
            bindingResult.getFieldErrors().forEach(fieldError -> fieldAndMessage.put(fieldError.getField(),
                    fieldError.getDefaultMessage()));
            throw new FormException(fieldAndMessage);
        }
        animalService.addAnimal(addAnimalDTO, appUser.getUsername());

        LOGGER.info("Added animal: {}", addAnimalDTO.getSpecieName());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<AnimalDTO>>> allAnimals() {
        List<AnimalDTO> animals = animalService.allAnimals();

        for (AnimalDTO animal : animals) {
            Optional<Picture> picture = pictureService.animalPictureByName(animal.getSpecieName());
            picture.ifPresent(value -> animal.setUrl(value.getUrl()));
        }

        List<EntityModel<AnimalDTO>> animalModels = animals.stream()
                .map(animal -> EntityModel.of(animal,
                        linkTo(methodOn(AnimalRestController.class).animalByName(animal.getSpecieName())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<AnimalDTO>> collectionModel = CollectionModel.of(animalModels);

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{specie-name}")
    public ResponseEntity<EntityModel<AnimalDetailsDTO>> animalByName(@PathVariable("specie-name") String name) {
        AnimalDetailsDTO animal = animalService.animalByName(name);

        Optional<Picture> picture = pictureService.animalPictureByName(animal.getSpecieName());
        picture.ifPresent(value -> animal.setUrl(value.getUrl()));

        return ResponseEntity.ok(EntityModel.of(animal,
                linkTo(methodOn(AnimalRestController.class).animalByName(name)).withSelfRel()));
    }
}
