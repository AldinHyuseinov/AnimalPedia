package bg.softuni.animalpedia.web.restcontrollers;

import bg.softuni.animalpedia.models.dto.AddAnimalDTO;
import bg.softuni.animalpedia.models.enums.*;
import bg.softuni.animalpedia.models.enums.Class;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalRestControllerIT {
    @Autowired
    private MockMvc mockMvc;

    private static final String API_URL = "/api/animals/";

    @Test
    @WithUserDetails("usercheto")
    void testAddingAnimal() throws Exception {
        AddAnimalDTO animalDTO = new AddAnimalDTO();
        animalDTO.setSpecieName("Test Lion");
        animalDTO.setPhylumType(PhylumType.CHORDATE);
        animalDTO.setAnimalClass(Class.MAMMAL);
        animalDTO.setScientificName("Panthera leo");
        animalDTO.setLocations(Set.of(Continent.AFRICA));

        animalDTO.setAnimalOrder("Carnivora");
        animalDTO.setAnimalFamily("Felidae");
        animalDTO.setGenus("Panthera");
        animalDTO.setConservationStatus(Status.VULNERABLE);
        animalDTO.setHabitat("computer");
        animalDTO.setDietType(DietType.CARNIVOROUS);
        animalDTO.setSkinType(SkinType.FUR);
        animalDTO.setLifespan(10);
        animalDTO.setDescription("The test lion is a species of large cat that lives inside a test.");

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(post(API_URL + "add").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(animalDTO))).andExpect(status().isCreated());

    }
    //animals from data.sql
    @Test
    void testGettingAllAnimals() throws Exception {
        mockMvc.perform(get(API_URL + "all")).andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.animalDTOList", hasSize(7)))
                .andExpect(jsonPath("$._embedded.animalDTOList[0].url", is(nullValue())))
                .andExpect(jsonPath("$._embedded.animalDTOList[0].specieName", is("African elephant")))
                .andExpect(jsonPath("$._embedded.animalDTOList[0].description", is("The African elephant is a species in the family " +
                        "Elephantidae and a member of the genus Loxodonta. It is the largest living terrestrial animal with bulls reaching up to 4 m tall at the shoulder.")))
                .andExpect(jsonPath("$._embedded.animalDTOList[0].addedByUsername", is("admincheto")))
                .andExpect(jsonPath("$._embedded.animalDTOList[0].detailsLink", is(nullValue())))
                .andExpect(jsonPath("$._embedded.animalDTOList[0].deleteLink", is(nullValue())))
                .andExpect(jsonPath("$._embedded.animalDTOList[0]._links.self.href", is("http://localhost" + API_URL + "African%20elephant")))
                .andExpect(jsonPath("$._embedded.animalDTOList[0]._links.delete.href", is("http://localhost" + API_URL + "delete/African%20elephant")));
    }

    @Test
    void testGettingASingleAnimal() throws Exception {
        mockMvc.perform(get(API_URL + "African elephant")).andExpect(status().isOk())
                .andExpect(jsonPath("$.url", is(nullValue())))
                .andExpect(jsonPath("$.specieName", is("African elephant")))
                .andExpect(jsonPath("$.description", is("The African elephant is a species in the family " +
                        "Elephantidae and a member of the genus Loxodonta. It is the largest living terrestrial animal with bulls reaching up to 4 m tall at the shoulder.")))
                .andExpect(jsonPath("$.addedByUsername", is("admincheto")))
                .andExpect(jsonPath("$.phylumType", is(PhylumType.CHORDATE.name())))
                .andExpect(jsonPath("$.animalClass", is(Class.MAMMAL.name())))
                .andExpect(jsonPath("$.animalOrder", is("Proboscidea")))
                .andExpect(jsonPath("$.animalFamily", is("Elephantidae")))
                .andExpect(jsonPath("$.genus", is("Loxodonta")))
                .andExpect(jsonPath("$.scientificName", is("L. africana")))
                .andExpect(jsonPath("$.continents", hasSize(1)))
                .andExpect(jsonPath("$.conservationStatus", is(Status.CRITICALLY_ENDANGERED.name())))
                .andExpect(jsonPath("$.habitat", is("Savannahs grasslands woodlands wetlands and forests")))
                .andExpect(jsonPath("$.dietType", is(DietType.OMNIVOROUS.name())))
                .andExpect(jsonPath("$.skinType", is(SkinType.FUR.name())))
                .andExpect(jsonPath("$.lifespan", is(70)))
                .andExpect(jsonPath("$.verified", is(false)));
    }
}
