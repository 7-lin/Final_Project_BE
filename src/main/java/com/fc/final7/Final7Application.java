package com.fc.final7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Final7Application {

	public static void main(String[] args) {
		SpringApplication.run(Final7Application.class, args);
	}

}
