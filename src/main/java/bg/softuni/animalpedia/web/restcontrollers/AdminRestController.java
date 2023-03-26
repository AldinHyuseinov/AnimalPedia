package bg.softuni.animalpedia.web.restcontrollers;

import bg.softuni.animalpedia.models.dto.BannedUserDTO;
import bg.softuni.animalpedia.models.dto.UserDTO;
import bg.softuni.animalpedia.services.BanService;
import bg.softuni.animalpedia.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AdminRestController {
    private final UserService userService;

    private final BanService banService;

    @GetMapping("/all-users")
    public ResponseEntity<CollectionModel<EntityModel<UserDTO>>> allUsers() {
        List<EntityModel<UserDTO>> entityModels = userService.allUsers().
                stream().peek(user -> user.setBanned(banService.isBanned(user.getUsername())))
                .map(user -> EntityModel.of(user, getUserLinks(user))).toList();

        CollectionModel<EntityModel<UserDTO>> collectionModel = CollectionModel.of(entityModels);

        return ResponseEntity.ok(collectionModel);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/promote/{username}")
    public ResponseEntity<?> promoteUser(@PathVariable String username) {
        userService.promoteUser(username);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/demote/{username}")
    public ResponseEntity<?> demoteUser(@PathVariable String username) {
        userService.demoteUser(username);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ban")
    public ResponseEntity<?> banUser(@RequestBody BannedUserDTO bannedUserDTO) {

        if (banService.isBanned(bannedUserDTO.getUserUsername())) {
            return ResponseEntity.status(HttpStatus.FOUND).build();
        }
        banService.banUser(bannedUserDTO);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/unban/{username}")
    public ResponseEntity<?> unbanUser(@PathVariable String username) {
        banService.unbanUser(username);

        return ResponseEntity.ok().build();
    }

    private Link[] getUserLinks(UserDTO userDTO) {
        List<Link> userLinks = new ArrayList<>();

        Link promote = linkTo(methodOn(AdminRestController.class).promoteUser(userDTO.getUsername())).withRel("promote");
        Link demote = linkTo(methodOn(AdminRestController.class).demoteUser(userDTO.getUsername())).withRel("demote");

        if (banService.isBanned(userDTO.getUsername())) {
            Link unban = linkTo(methodOn(AdminRestController.class).unbanUser(userDTO.getUsername())).withRel("unban");
            userLinks.add(unban);
        }

        userLinks.add(promote);
        userLinks.add(demote);

        return userLinks.toArray(new Link[0]);
    }
}
