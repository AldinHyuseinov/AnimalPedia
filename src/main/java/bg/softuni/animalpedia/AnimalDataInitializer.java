package bg.softuni.animalpedia;

import bg.softuni.animalpedia.repositories.AnimalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Component
public class AnimalDataInitializer implements CommandLineRunner {
    private final AnimalRepository animalRepository;

    private final JdbcTemplate jdbcTemplate;

    private static final Path DATA_PATH = Path.of("src/main/resources/data.sql");

    public AnimalDataInitializer(AnimalRepository animalRepository, JdbcTemplate jdbcTemplate) {
        this.animalRepository = animalRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

        if (animalRepository.count() <= 1) {
            Arrays.stream(Files.readString(DATA_PATH).split(";")).forEach(jdbcTemplate::execute);
        }
    }
}
