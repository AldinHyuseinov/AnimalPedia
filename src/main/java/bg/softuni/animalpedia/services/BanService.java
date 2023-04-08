package bg.softuni.animalpedia.services;

import bg.softuni.animalpedia.models.dto.BannedUserDTO;
import bg.softuni.animalpedia.models.entities.BannedUser;
import bg.softuni.animalpedia.repositories.BannedUserRepository;
import bg.softuni.animalpedia.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

        if (!bannedUserDTO.getBanTime().isBlank()) {
            bannedUser.setBannedUntil(LocalDateTime.now().plusDays(Long.parseLong(bannedUserDTO.getBanTime())));
        }
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

    public String getBanTime(String username) {
        LocalDateTime bannedUntil = bannedUserRepository.findByUserUsername(username).get().getBannedUntil();

        if (bannedUntil == null) {
            return "Permanent";
        }
        return bannedUntil.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    @Scheduled(fixedRate = 7200000) // Runs every 2 hours
    public void banExpirationCheckAndRemove() {
        List<BannedUser> bannedUsers = bannedUserRepository.getAllByBannedUntilNotNull();
        bannedUsers.removeIf(bannedUser -> bannedUser.getBannedUntil().isAfter(LocalDateTime.now()));

        bannedUserRepository.saveAll(bannedUsers);
    }
}
