package bg.softuni.animalpedia.web.controllers;

import bg.softuni.animalpedia.models.dto.UserDTO;
import bg.softuni.animalpedia.models.dto.UserLinksDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private final RestTemplate restTemplate;

    private static final String API_URL = "http://localhost:8000/api/admin/all-users";

    public UserController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/profile")
    public String userProfile() {
        return "user-profile";
    }

    @GetMapping("/edit")
    public String userEdit() {
        return "user-edit";
    }

    @GetMapping("/all")
    public String allUsers(Model model) {
        ParameterizedTypeReference<CollectionModel<EntityModel<UserDTO>>> responseType = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<CollectionModel<EntityModel<UserDTO>>> responseEntity = restTemplate.exchange(API_URL, HttpMethod.GET, null, responseType);
        CollectionModel<EntityModel<UserDTO>> users = responseEntity.getBody();

        List<UserDTO> userList = users.getContent().stream().map(user -> {
            String promoteHref = user.getLink("promote").get().getHref();
            String demoteHref = user.getLink("demote").get().getHref();

            UserLinksDTO userLinksDTO = new UserLinksDTO();
            userLinksDTO.setPromoteLink(promoteHref);
            userLinksDTO.setDemoteLink(demoteHref);

            Optional<Link> unbanLink = user.getLink("unban");
            unbanLink.ifPresent(link -> userLinksDTO.setUnbanLink(unbanLink.get().getHref()));

            user.getContent().setUserLinks(userLinksDTO);
            return user.getContent();
        }).toList();

        model.addAttribute("users", userList);
        return "users-all";
    }
}
