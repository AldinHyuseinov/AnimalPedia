package bg.softuni.animalpedia.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/animals")
public class AnimalController {

    @GetMapping("/add")
    public String addAnimalPage() {
        return "animal-add";
    }
}
