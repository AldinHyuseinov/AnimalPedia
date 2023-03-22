package bg.softuni.animalpedia.web.controllers;

import bg.softuni.animalpedia.AnimalOfTheDayTask;
import bg.softuni.animalpedia.models.dto.AnimalOfTheDayDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor(onConstructor_ = @Autowired)
public class HomeController {
    private final AnimalOfTheDayTask animalOfTheDayTask;

    @GetMapping("/")
    public String home(Model model) {
        AnimalOfTheDayDTO animalOfTheDayDTO = animalOfTheDayTask.getAnimalOfTheDay();

        if (animalOfTheDayDTO != null) {
            model.addAttribute("noAnimalOfTheDay", false);
            model.addAttribute("animalOfTheDay", animalOfTheDayTask.getAnimalOfTheDay());
        } else {
            model.addAttribute("noAnimalOfTheDay", true);
        }
        return "index";
    }
}
