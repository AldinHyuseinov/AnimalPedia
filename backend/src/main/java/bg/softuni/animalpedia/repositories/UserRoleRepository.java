package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.UserRole;
import bg.softuni.animalpedia.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRole(Role role);
}
