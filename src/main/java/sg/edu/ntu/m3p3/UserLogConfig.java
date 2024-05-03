package sg.edu.ntu.m3p3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UserLogConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
