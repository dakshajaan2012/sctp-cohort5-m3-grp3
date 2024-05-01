package sg.edu.ntu.m3p3.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import sg.edu.ntu.m3p3.entity.User.CreateUserRequest;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String basicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        return "Basic " + new String(encodedAuth);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Create a user")
    @Test
    public void testCreateUser() throws Exception {
        // Create a CreateUserRequest object with sample data
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .userName("JohnDoe")
                .password("Password1")
                .firstName("John")
                .lastName("Doe")
                .email("test@example.com")
                .isAdmin(false)
                .build();

        // Convert the createUserRequest object to JSON
        String newUserAsJson = objectMapper.writeValueAsString(createUserRequest);

        // Create a POST request to create the user
        RequestBuilder request = MockMvcRequestBuilders.post("/user/create").contentType(MediaType.APPLICATION_JSON)
                .content(newUserAsJson).header("Authorization", basicAuthHeader("user", "password"));

        System.err.println("REQUEST!");
        System.err.println(request);
        // Perform the POST request and validate the response
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.requestId").exists())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.userName").value("JohnDoe"))
                .andExpect(jsonPath("$.data.firstName").value("John"))
                .andExpect(jsonPath("$.data.lastName").value("Doe"))
                .andExpect(jsonPath("$.data.password").value("Password1"))
                .andExpect(jsonPath("$.data.email").value("test@example.com"))
                .andExpect(jsonPath("$.data.isAdmin").value(false));
    }

}
