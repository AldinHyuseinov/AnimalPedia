package bg.softuni.animalpedia.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class AnimalDTO {
    private Long id;

    private String specieName;

    private String url;

    private String description;

    private String addedByUsername;

    private LocalDateTime created;

    private String detailsLink;
}
