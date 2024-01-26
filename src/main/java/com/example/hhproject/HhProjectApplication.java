package com.example.hhproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HhProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(HhProjectApplication.class, args);
	}

}
