package by.bogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoteApplication {

	public static void main(String[] args) {

		SpringApplication.run(new Class<?>[] {VoteApplication.class, JpaConfig.class}, args);
		
	}
}
