package bg.softuni.animalpedia.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PictureDTO {
    private String title;

    private String url;

    private String authorUsername;
}
