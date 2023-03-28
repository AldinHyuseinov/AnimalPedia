package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    void deleteByUsername(String username);

    Set<User> findAllByVerificationCodeNotNull();
}
