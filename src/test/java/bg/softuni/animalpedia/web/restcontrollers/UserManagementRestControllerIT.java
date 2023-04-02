package bg.softuni.animalpedia.web.restcontrollers;

import bg.softuni.animalpedia.models.dto.BannedUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserManagementRestControllerIT {
    @Autowired
    private MockMvc mockMvc;

    private static final String API_URL = "/api/admin/";

    // users for the tests are from data.sql
    @Test
    void testGettingAllUsers() throws Exception {

        mockMvc.perform(get(API_URL + "all-users")).andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.userDTOList", hasSize(1)))
                .andExpect(jsonPath("$._embedded.userDTOList[0].imageUrl", is(nullValue())))
                .andExpect(jsonPath("$._embedded.userDTOList[0].username", is("usercheto")))
                .andExpect(jsonPath("$._embedded.userDTOList[0].firstName", is("User")))
                .andExpect(jsonPath("$._embedded.userDTOList[0].lastName", is("Userov")))
                .andExpect(jsonPath("$._embedded.userDTOList[0].email", is("User@example.com")))
                .andExpect(jsonPath("$._embedded.userDTOList[0].role", is("USER")))
                .andExpect(jsonPath("$._embedded.userDTOList[0].userLinks", is(nullValue())))
                .andExpect(jsonPath("$._embedded.userDTOList[0].banned", is(false)))
                .andExpect(jsonPath("$._embedded.userDTOList[0]._links.promote.href", is("http://localhost" + API_URL + "promote/usercheto")))
                .andExpect(jsonPath("$._embedded.userDTOList[0]._links.demote.href", is("http://localhost" + API_URL + "demote/usercheto")));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testPromotingAndDemotingUser() throws Exception {
        mockMvc.perform(patch(API_URL + "/promote/usercheto")).andExpect(status().isOk());
        mockMvc.perform(patch(API_URL + "/promote/usercheto")).andExpect(status().isBadRequest());

        mockMvc.perform(patch(API_URL + "/demote/usercheto")).andExpect(status().isOk());
        mockMvc.perform(patch(API_URL + "/demote/usercheto")).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testBanningAndUnbanningUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        BannedUserDTO bannedUserDTO = new BannedUserDTO("usercheto", "bad user", "");

        mockMvc.perform(post(API_URL + "/ban").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bannedUserDTO))).andExpect(status().isOk());

        mockMvc.perform(post(API_URL + "/ban").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bannedUserDTO))).andExpect(status().isFound());

        mockMvc.perform(delete(API_URL + "/unban/usercheto").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bannedUserDTO))).andExpect(status().isOk());

        mockMvc.perform(delete(API_URL + "/unban/usercheto").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bannedUserDTO))).andExpect(status().isNotFound());
    }
}
