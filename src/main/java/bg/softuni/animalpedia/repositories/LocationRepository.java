package bg.softuni.animalpedia.repositories;

import bg.softuni.animalpedia.models.entities.Location;
import bg.softuni.animalpedia.models.enums.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Set<Location> getAllByContinentIn(Set<Continent> continents);
}
