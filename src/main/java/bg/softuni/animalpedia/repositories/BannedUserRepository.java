package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.BannedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface BannedUserRepository extends JpaRepository<BannedUser, Long> {
    Optional<BannedUser> findByUserUsername(String username);

    @Modifying
    @Transactional
    void deleteByUserUsername(String username);
}
