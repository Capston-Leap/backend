package com.dash.leap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LeapApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeapApplication.class, args);
	}

}
