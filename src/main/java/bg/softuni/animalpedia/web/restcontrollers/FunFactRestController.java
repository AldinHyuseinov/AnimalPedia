package bg.softuni.animalpedia.web.restcontrollers;

import bg.softuni.animalpedia.models.AppUser;
import bg.softuni.animalpedia.models.dto.FunFactDTO;
import bg.softuni.animalpedia.services.FunFactService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static bg.softuni.animalpedia.utils.ErrorHelper.hasErrors;

@RestController
@RequestMapping("/api/fun-fact")
public class FunFactRestController {
    private final FunFactService funFactService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FunFactRestController.class);

    public FunFactRestController(FunFactService funFactService) {
        this.funFactService = funFactService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFact(@RequestBody @Valid FunFactDTO funFactDTO, BindingResult bindingResult, @AuthenticationPrincipal AppUser appUser) {
        hasErrors(bindingResult);
        funFactService.addFunFact(funFactDTO, appUser.getUsername());

        LOGGER.info("Added fun fact for animal: {}", funFactDTO.getForAnimalSpecieName());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{specie-name}")
    public ResponseEntity<String> randomFact(@PathVariable("specie-name") String name) {
        return ResponseEntity.ok(funFactService.getRandomFunFactAbout(name));
    }
}
