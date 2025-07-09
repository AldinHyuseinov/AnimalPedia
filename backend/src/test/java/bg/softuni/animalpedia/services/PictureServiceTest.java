package bg.softuni.animalpedia.services;

import bg.softuni.animalpedia.models.dto.AddPictureDTO;
import bg.softuni.animalpedia.models.entities.Animal;
import bg.softuni.animalpedia.models.entities.Picture;
import bg.softuni.animalpedia.models.entities.User;
import bg.softuni.animalpedia.repositories.AnimalRepository;
import bg.softuni.animalpedia.repositories.PictureRepository;
import bg.softuni.animalpedia.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PictureServiceTest {
    private PictureService toTest;

    @Mock
    private PictureRepository mockPictureRepository;

    @Mock
    private AnimalRepository mockAnimalRepository;

    @Mock
    private UserRepository mockUserRepository;

    private static final String SPECIE_NAME = "Leopard";

    private static final String USERNAME = "Pesho";

    private Animal mockAnimal;

    private Picture mockPicture;

    @BeforeEach
    void setup() {
        toTest = new PictureService(mockPictureRepository, mockAnimalRepository, mockUserRepository, new ModelMapper());

        mockAnimal = new Animal();
        mockAnimal.setAnimalOrder("Carnivora");
        mockAnimal.setAnimalFamily("Felidae");
        mockAnimal.setGenus("Panthera");
        mockAnimal.setSpecieName(SPECIE_NAME);
        mockAnimal.setScientificName("Panthera pardus");
        mockAnimal.setHabitat("Forests, grasslands, and savannas");
        mockAnimal.setDescription("The leopard is a large cat native to Africa and Asia.");

        mockPicture = new Picture();
        mockPicture.setTitle("A leopard");
        mockPicture.setAnimal(mockAnimal);
        mockPicture.setUrl("An url");
    }

    @Test
    void testAddingPicture() {
        AddPictureDTO pictureDTO = new AddPictureDTO();
        pictureDTO.setTitle("Title");
        pictureDTO.setUrl("An url");
        pictureDTO.setSpecieName(SPECIE_NAME);

        User mockUser = new User();
        mockUser.setUsername(USERNAME);
        mockUser.setFirstName("Petur");
        mockUser.setLastName("Petrov");

        when(mockAnimalRepository.findBySpecieName(SPECIE_NAME)).thenReturn(Optional.of(mockAnimal));
        when(mockUserRepository.findByUsername(USERNAME)).thenReturn(Optional.of(mockUser));
        when(mockPictureRepository.findByUrl("An url")).thenReturn(mockPicture);

        toTest.addPicture(pictureDTO, USERNAME);

        when(mockPictureRepository.findByUrl("An url")).thenReturn(null);

        toTest.addPicture(pictureDTO, USERNAME);

        verify(mockPictureRepository, times(2)).save(any());
    }

    @Test
    void testAnimalPictureByName() {
        when(mockPictureRepository.findFirstByAnimal(mockAnimal)).thenReturn(Optional.of(mockPicture));
        when(mockAnimalRepository.findBySpecieName(mockAnimal.getSpecieName())).thenReturn(Optional.of(mockAnimal));

        assertEquals(toTest.animalPictureByName(mockAnimal.getSpecieName()), Optional.of(mockPicture));
    }

    @Test
    void testAllAnimalPicturesByName() {
        when(mockPictureRepository.findAllByAnimalSpecieName(mockAnimal.getSpecieName())).thenReturn(List.of(mockPicture));

        assertEquals(1, toTest.allAnimalPicturesByName(mockAnimal.getSpecieName()).size());
    }
}
