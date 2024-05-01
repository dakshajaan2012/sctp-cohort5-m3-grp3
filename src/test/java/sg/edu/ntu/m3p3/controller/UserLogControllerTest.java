package sg.edu.ntu.m3p3.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import sg.edu.ntu.m3p3.entity.UserLog;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Integration test

    /*
     * @Test
     * public void getAllUserLogsTest() throws Exception{
     * RequestBuilder request = MockMvcRequestBuilders.get("/user-logs");
     * mockMvc.perform(request)
     * // Assert that the status code is 200
     * .andExpect(status().isOk())
     * .andExpect(content().contentType(MediaType.APPLICATION_JSON))
     * .andExpect(jsonPath("$.size()").value(1));
     * }
     */

    @Autowired
    public ObjectMapper objectMapper;

    /*
     * @Test
     * public void validUserLogTest() throws Exception {
     * // Create a userlog object
     * UserLog newUserLog =
     * UserLog.builder().ipAddress("192.168.0.1").origin("singapore").build();
     * 
     * // Convert Java object to json using objectmapper
     * String newUserLogAsJSON = objectMapper.writeValueAsString(newUserLog);
     * 
     * RequestBuilder request = MockMvcRequestBuilders.post(
     * "/user/97473a6e-c51c-4e76-9eb4-24a56f64a413/user-logs")
     * .contentType(MediaType.APPLICATION_JSON)
     * .content(newUserLogAsJSON);
     * 
     * mockMvc.perform(request)
     * .andExpect(status().isCreated())
     * .andExpect(content().contentType(MediaType.APPLICATION_JSON))
     * .andExpect(jsonPath("$.ipAddress").value("192.168.0.1"))
     * .andExpect(jsonPath("$.origin").value("singapore"));
     * }
     */

    @Test
    public void deleteUserLogTest() throws Exception {
        // Perform DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/user-logs/{userLogId}", "b8830a61-0c78-44a1-99ed-f4143e77d3ca")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
