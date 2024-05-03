package sg.edu.ntu.m3p3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("sg.edu.ntu.m3p3.entity")
@EnableJpaRepositories("sg.edu.ntu.m3p3.repository")
public class M3P3Application {

	public static void main(String[] args) {
		SpringApplication.run(M3P3Application.class, args);
	}

}
