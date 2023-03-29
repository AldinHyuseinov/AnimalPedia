package bg.softuni.animalpedia.web.controllers;

import bg.softuni.animalpedia.models.AppUser;
import bg.softuni.animalpedia.models.dto.AddPictureDTO;
import bg.softuni.animalpedia.services.PictureService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.animalpedia.utils.ErrorHelper.hasErrors;

@Controller
@RequestMapping("/pictures")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class PictureController {
    private final PictureService pictureService;

    @PostMapping("/upload/{specie-name}")
    public String uploadPicture(@Valid AddPictureDTO pictureModel, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, @AuthenticationPrincipal AppUser appUser, @PathVariable("specie-name") String name) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.pictureModel", bindingResult);
            redirectAttributes.addFlashAttribute("pictureModel", pictureModel);
            return "redirect:/animals/" + name;
        }

        pictureModel.setSpecieName(name);
        pictureService.addPicture(pictureModel, appUser.getUsername());

        return "redirect:/animals/" + name;
    }
}
