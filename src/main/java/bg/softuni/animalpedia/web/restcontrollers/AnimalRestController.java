package bg.softuni.animalpedia.web.restcontrollers;

import bg.softuni.animalpedia.models.AppUser;
import bg.softuni.animalpedia.models.dto.AddAnimalDTO;
import bg.softuni.animalpedia.models.dto.AnimalDTO;
import bg.softuni.animalpedia.models.dto.AnimalDetailsDTO;
import bg.softuni.animalpedia.models.dto.EditAnimalDTO;
import bg.softuni.animalpedia.models.entities.Picture;
import bg.softuni.animalpedia.services.AnimalService;
import bg.softuni.animalpedia.services.PictureService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static bg.softuni.animalpedia.utils.ErrorHelper.hasErrors;
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

        hasErrors(bindingResult);
        animalService.addAnimal(addAnimalDTO, appUser.getUsername());
        LOGGER.info("Added animal: {}", addAnimalDTO.getSpecieName());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<AnimalDTO>>> allAnimals() {
        List<AnimalDTO> animals = animalService.allAnimals();

        CollectionModel<EntityModel<AnimalDTO>> collectionModel = getModels(animals);

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

    @PreAuthorize("@userService.userAuthorizationCheck(#authentication, #name)")
    @DeleteMapping("/delete/{specie-name}")
    public ResponseEntity<?> deleteAnimal(@PathVariable("specie-name") String name) {
        animalService.deleteAnimal(name);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("@userService.userAuthorizationCheck(#authentication, #editAnimalDTO.specieName)")
    @PutMapping("/edit")
    public ResponseEntity<?> editAnimal(@RequestBody @Valid EditAnimalDTO editAnimalDTO, BindingResult bindingResult) {
        hasErrors(bindingResult);
        animalService.editAnimal(editAnimalDTO);
        LOGGER.info("Edited animal: {}", editAnimalDTO.getSpecieName());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/verify/{specie-name}")
    public ResponseEntity<?> verifyAnimal(@PathVariable("specie-name") String name) {
        animalService.verifyAnimal(name);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/unverify/{specie-name}")
    public ResponseEntity<?> unverifyAnimal(@PathVariable("specie-name") String name) {
        animalService.unverifyAnimal(name);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/{search-term}")
    public ResponseEntity<CollectionModel<EntityModel<AnimalDTO>>> searchAnimals(@PathVariable("search-term") String search) {
        List<AnimalDTO> animals = animalService.searchAnimals(search);

        CollectionModel<EntityModel<AnimalDTO>> collectionModel = getModels(animals);

        return ResponseEntity.ok(collectionModel);
    }

    private CollectionModel<EntityModel<AnimalDTO>> getModels(List<AnimalDTO> animals) {

        for (AnimalDTO animal : animals) {
            Optional<Picture> picture = pictureService.animalPictureByName(animal.getSpecieName());
            picture.ifPresent(value -> animal.setUrl(value.getUrl()));
        }

        List<EntityModel<AnimalDTO>> animalModels = animals.stream()
                .map(animal -> EntityModel.of(animal,
                        linkTo(methodOn(AnimalRestController.class).animalByName(animal.getSpecieName())).withSelfRel(),
                        linkTo(methodOn(AnimalRestController.class).deleteAnimal(animal.getSpecieName())).withRel("delete")))
                .collect(Collectors.toList());

        return CollectionModel.of(animalModels);
    }
}
