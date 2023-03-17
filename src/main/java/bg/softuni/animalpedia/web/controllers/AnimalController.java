package bg.softuni.animalpedia.web.controllers;

import bg.softuni.animalpedia.models.dto.AnimalDTO;
import bg.softuni.animalpedia.models.dto.AnimalDetailsDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/animals")
public class AnimalController {
    private final RestTemplate restTemplate;

    private static final String API_URL = "http://localhost:8000/api/animals/";

    public AnimalController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/add")
    public String addAnimalPage() {
        return "animal-add";
    }

    @GetMapping("/all")
    public String allAnimalsPage(Model model) {
        ResponseEntity<CollectionModel<EntityModel<AnimalDTO>>> response = restTemplate.exchange(API_URL + "all",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        CollectionModel<EntityModel<AnimalDTO>> animals = response.getBody();

        List<AnimalDTO> animalList = animals.getContent().stream().map(animal -> {
            String href = animal.getLink("self").get().getHref();
            animal.getContent().setDetailsLink(href.replace("/api", ""));
            return animal.getContent();
        }).toList();

        model.addAttribute("animals", animalList);
        return "animals-all";
    }

    @GetMapping("/{specie-name}")
    public String animal(@PathVariable("specie-name") String name, Model model) {
        ResponseEntity<EntityModel<AnimalDetailsDTO>> response = restTemplate.exchange(API_URL + name,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        EntityModel<AnimalDetailsDTO> animal = response.getBody();
        model.addAttribute("animal", animal.getContent());

        return "animal-details";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(Exception.class)
    public ModelAndView onAnimalNotFoundInApi() {
        return new ModelAndView("animal-not-found");
    }
}
