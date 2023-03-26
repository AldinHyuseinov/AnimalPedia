package bg.softuni.animalpedia.services;

import bg.softuni.animalpedia.models.dto.BannedUserDTO;
import bg.softuni.animalpedia.models.entities.BannedUser;
import bg.softuni.animalpedia.repositories.BannedUserRepository;
import bg.softuni.animalpedia.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class BanService {
    private final BannedUserRepository bannedUserRepository;

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    public boolean isBanned(String username) {
        return bannedUserRepository.findByUserUsername(username).isPresent();
    }

    public void banUser(BannedUserDTO bannedUserDTO) {
        BannedUser bannedUser = mapper.map(bannedUserDTO, BannedUser.class);
        bannedUser.setUser(userRepository.findByUsername(bannedUserDTO.getUserUsername()).orElse(null));

        bannedUserRepository.save(bannedUser);
    }

    public void unbanUser(String username) {

        if (!isBanned(username)) {
            throw new NoSuchElementException("User is not banned!");
        }
        bannedUserRepository.deleteByUserUsername(username);
    }

    public BannedUserDTO bannedUser(String username) {
        return mapper.map(bannedUserRepository.findByUserUsername(username).get(), BannedUserDTO.class);
    }
}
