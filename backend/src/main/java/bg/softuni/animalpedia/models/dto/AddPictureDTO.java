package bg.softuni.animalpedia.models.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AddPictureDTO {
    @NotEmpty(message = "Title is required.")
    private String title;

    private String url;

    private String specieName;
}
