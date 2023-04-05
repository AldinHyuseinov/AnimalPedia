package bg.softuni.animalpedia.web.controllers;

import bg.softuni.animalpedia.models.dto.AddPictureDTO;
import bg.softuni.animalpedia.models.dto.AnimalDTO;
import bg.softuni.animalpedia.models.dto.AnimalDetailsDTO;
import bg.softuni.animalpedia.services.PictureService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/animals")
public class AnimalController {
    private final RestTemplate restTemplate;

    private final PictureService pictureService;

    private static final String API_URL = "http://localhost:8000/api/animals/";

    public AnimalController(RestTemplate restTemplate, PictureService pictureService) {
        this.restTemplate = restTemplate;
        this.pictureService = pictureService;
    }

    @GetMapping("/add")
    public String addAnimalPage() {
        return "animal-add";
    }

    @GetMapping("/all")
    public String allAnimalsPage(Model model, @PageableDefault(size = 5) Pageable pageable) {
        ResponseEntity<CollectionModel<EntityModel<AnimalDTO>>> response = restTemplate.exchange(API_URL + "all",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        process(model, pageable, response);
        return "animals-all";
    }

    @ModelAttribute("pictureModel")
    public AddPictureDTO initPicture() {
        return new AddPictureDTO();
    }

    @GetMapping("/{specie-name}")
    public String animal(@PathVariable("specie-name") String name, Model model) {
        ResponseEntity<EntityModel<AnimalDetailsDTO>> response = restTemplate.exchange(API_URL + name,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        EntityModel<AnimalDetailsDTO> animal = response.getBody();

        model.addAttribute("animal", animal.getContent());
        model.addAttribute("animalPictures", pictureService.allAnimalPicturesByName(name));

        return "animal-details";
    }

    @GetMapping("/search")
    public String search(@RequestParam String search, Model model, @PageableDefault(size = 5) Pageable pageable) {

        if (!search.isBlank()) {
            ResponseEntity<CollectionModel<EntityModel<AnimalDTO>>> response = restTemplate.exchange(API_URL + "search/" + search,
                    HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                    });
            process(model, pageable, response);
        } else {
            model.addAttribute("noAnimals", true);
        }

        model.addAttribute("searchTerm", search);
        return "animal-search-results";
    }

    private void process(Model model, Pageable pageable, ResponseEntity<CollectionModel<EntityModel<AnimalDTO>>> response) {
        CollectionModel<EntityModel<AnimalDTO>> animals = response.getBody();

        List<AnimalDTO> animalList = animals.getContent().stream().map(animal -> {
            setLinks(animal);
            return animal.getContent();
        }).toList();

        if (animalList.isEmpty()) {
            model.addAttribute("noAnimals", true);
            return;
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), animalList.size());

        Page<AnimalDTO> animalsPage = new PageImpl<>(animalList.subList(start, end), pageable, animalList.size());

        model.addAttribute("animals", animalsPage);
    }

    private void setLinks(EntityModel<AnimalDTO> animal) {
        String selfHref = animal.getLink("self").get().getHref();
        animal.getContent().setDetailsLink(selfHref.replace("/api", ""));
        String deleteHref = animal.getLink("delete").get().getHref();
        animal.getContent().setDeleteLink(deleteHref);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(HttpClientErrorException.class)
    public ModelAndView onAnimalNotFoundInApi() {
        return new ModelAndView("animal-not-found");
    }
}
