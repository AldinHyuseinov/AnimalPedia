package bg.softuni.animalpedia.services;

import bg.softuni.animalpedia.models.entities.ConservationStatus;
import bg.softuni.animalpedia.models.enums.Status;
import bg.softuni.animalpedia.repositories.ConservationStatusRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ConservationStatusService {
    private final ConservationStatusRepository conservationStatusRepository;

    @PostConstruct
    private void initStatuses() {
        Arrays.stream(Status.values()).map(status -> {
            ConservationStatus conservationStatus = new ConservationStatus();
            conservationStatus.setStatus(status);
            return conservationStatus;
        }).forEach(conservationStatusRepository::save);
    }
}
