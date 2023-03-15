package bg.softuni.animalpedia.web.restcontrollers;

import bg.softuni.animalpedia.models.AppUser;
import bg.softuni.animalpedia.models.dto.AddAnimalDTO;
import bg.softuni.animalpedia.services.AnimalService;
import bg.softuni.animalpedia.utils.exceptions.FormErrorException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/animals")
public class AnimalRestController {
    private final AnimalService animalService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalRestController.class);

    public AnimalRestController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addAnimal(@RequestBody @Valid AddAnimalDTO addAnimalDTO, BindingResult bindingResult,
                                          @AuthenticationPrincipal AppUser appUser) {

        if (bindingResult.hasErrors()) {
            Map<String, String> fieldAndMessage = new HashMap<>();
            bindingResult.getFieldErrors().forEach(fieldError -> fieldAndMessage.put(fieldError.getField(),
                    fieldError.getDefaultMessage()));
            throw new FormErrorException(fieldAndMessage);
        }
        animalService.addAnimal(addAnimalDTO, appUser.getUsername());

        LOGGER.info("Added animal: {}", addAnimalDTO.getSpecieName());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
