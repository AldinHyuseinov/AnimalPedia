package bg.softuni.animalpedia.web.restcontrollers;

import bg.softuni.animalpedia.models.dto.EditUserDTO;
import bg.softuni.animalpedia.models.dto.RegisterUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Value("${mail.host}")
    private String mailHost;

    @Value("${mail.port}")
    private Integer mailPort;
    @Value("${mail.username}")
    private String mailUsername;
    @Value("${mail.password}")
    private String mailPassword;

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

        GreenMail greenMail = new GreenMail(new ServerSetup(mailPort, mailHost, "smtp"));
        greenMail.start();
        greenMail.setUser(mailUsername, mailPassword);

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

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        Assertions.assertEquals(1, receivedMessages.length);
        greenMail.stop();
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

    @Test
    //users from data.sql file
    @WithUserDetails("usercheto")
    void testDeletingUser() throws Exception {
        mockMvc.perform(delete("/api/users/delete").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
