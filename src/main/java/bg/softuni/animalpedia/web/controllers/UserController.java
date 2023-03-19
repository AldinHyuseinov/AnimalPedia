package bg.softuni.animalpedia.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/profile")
    public String userProfile() {
        return "user-profile";
    }

    @GetMapping("/edit")
    public String userEdit() {
        return "user-edit";
    }
}
