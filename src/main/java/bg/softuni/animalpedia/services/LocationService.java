package bg.softuni.animalpedia.services;

import bg.softuni.animalpedia.models.entities.Location;
import bg.softuni.animalpedia.models.enums.Continent;
import bg.softuni.animalpedia.repositories.LocationRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class LocationService {
    private final LocationRepository locationRepository;

    @PostConstruct
    private void initLocations() {

        if (locationRepository.count() == 0) {
            Arrays.stream(Continent.values()).map(continent -> {
                Location location = new Location();
                location.setContinent(continent);
                return location;
            }).forEach(locationRepository::save);
        }
    }
}
