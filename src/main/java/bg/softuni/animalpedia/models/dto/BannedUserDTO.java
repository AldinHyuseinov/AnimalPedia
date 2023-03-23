package bg.softuni.animalpedia.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BannedUserDTO {
    private String userUsername;

    private String reason;
}
