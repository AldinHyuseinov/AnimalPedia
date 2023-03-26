package bg.softuni.animalpedia.web.restcontrollers;

import bg.softuni.animalpedia.models.dto.EditUserDTO;
import bg.softuni.animalpedia.models.dto.RegisterUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerIT {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    private static final String USERNAME = "testuser";

    private static final String FIRST_NAME = "Test";

    private static final String LAST_NAME = "USER";

    private static final String EMAIL = "test@example.com";

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    void testRegister() throws Exception {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setUsername(USERNAME);
        registerUserDTO.setPassword("testpass");
        registerUserDTO.setConfirmPassword("testpass");
        registerUserDTO.setFirstName(FIRST_NAME);
        registerUserDTO.setLastName(LAST_NAME);
        registerUserDTO.setEmail(EMAIL);

        mockMvc.perform(post("/api/users/register").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(registerUserDTO))).andExpect(status().isCreated());

        registerUserDTO.setUsername("t");
        registerUserDTO.setPassword("t");
        registerUserDTO.setConfirmPassword("t");
        registerUserDTO.setFirstName("T");
        registerUserDTO.setLastName("U");
        registerUserDTO.setEmail("t");

        mockMvc.perform(post("/api/users/register").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerUserDTO))).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", aMapWithSize(6)));

        registerUserDTO.setUsername(USERNAME);
        registerUserDTO.setPassword("testpas");
        registerUserDTO.setConfirmPassword("testpass");
        registerUserDTO.setFirstName(FIRST_NAME);
        registerUserDTO.setLastName(LAST_NAME);
        registerUserDTO.setEmail(EMAIL);

        mockMvc.perform(post("/api/users/register").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerUserDTO))).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", aMapWithSize(3)));
    }

    @Test
    //users from data.sql file
    @WithUserDetails("usercheto")
    void testEditUser() throws Exception {
        EditUserDTO editUserDTO = new EditUserDTO();
        editUserDTO.setImageUrl("");
        editUserDTO.setUsername("usercheto");
        editUserDTO.setFirstName(FIRST_NAME);
        editUserDTO.setLastName(LAST_NAME);
        editUserDTO.setEmail("User@example.com");

        mockMvc.perform(put("/api/users/edit").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(editUserDTO))).andExpect(status().isOk());

        editUserDTO.setUsername("admincheto");
        editUserDTO.setEmail("Admin@example.com");

        mockMvc.perform(put("/api/users/edit").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(editUserDTO))).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", aMapWithSize(2)));
    }
}
