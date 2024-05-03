package sg.edu.ntu.m3p3.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import sg.edu.ntu.m3p3.entity.Address;
import sg.edu.ntu.m3p3.service.AddressService;
import sg.edu.ntu.m3p3.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @MockBean
    private UserService userService;

    private String basicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }

    @Test
    @DisplayName("Get address by user Id with Authentication")
    public void getAddressByUserId() throws Exception {
        UUID userId = UUID.randomUUID();
        Address mockAddress = new Address();
        mockAddress.setId(1L);

        given(userService.existsById(userId)).willReturn(true);

        given(addressService.findAddressesByUserId(userId)).willReturn(Arrays.asList(mockAddress));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/addresses/{userId}/address", userId.toString())
                .header("Authorization", basicAuthHeader("user", "password"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("Get address by userId and alias")
    public void getAddressByUserIdAndAlias() throws Exception {
        UUID userId = UUID.randomUUID();
        String alias = "home";
        Address mockAddress = new Address();
        mockAddress.setId(1L);

        given(addressService.findAddressesByUserIdAndAlias(userId, alias)).willReturn(Arrays.asList(mockAddress));

        mockMvc.perform(MockMvcRequestBuilders.get("/addresses/{userId}/address/", userId.toString())
                .param("alias", alias)
                .header("Authorization", basicAuthHeader("user", "password"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(mockAddress.getId()));
    }
}
