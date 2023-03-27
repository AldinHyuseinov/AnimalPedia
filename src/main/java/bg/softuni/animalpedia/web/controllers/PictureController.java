package bg.softuni.animalpedia.web.controllers;

import bg.softuni.animalpedia.models.dto.AddPictureDTO;
import bg.softuni.animalpedia.services.PictureService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pictures")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class PictureController {
    private final PictureService pictureService;

    @PostMapping("/upload/{specie-name}")
    public String uploadPicture(@Valid AddPictureDTO pictureModel, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Authentication auth, @PathVariable("specie-name") String name) {
        boolean isValid = false;

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.pictureModel", bindingResult);
            redirectAttributes.addFlashAttribute("pictureModel", pictureModel);
            return "redirect:/animals/" + name;
        }

        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            redirectAttributes.addFlashAttribute("userNotLoggedIn", "You must login to post a picture.");
        } else {
            isValid = true;
        }

        if (isValid) {
            pictureModel.setSpecieName(name);
            pictureService.addPicture(pictureModel, auth.getName());
        }
        return "redirect:/animals/" + name;
    }
}
