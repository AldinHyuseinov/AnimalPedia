package bg.softuni.animalpedia.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "banned_users")
@NoArgsConstructor
@Getter
@Setter
public class BannedUser extends BaseEntity {
    @OneToOne
    private User user;

    private String reason;

    private LocalDateTime bannedUntil;
}
