package com.errros.Restobar;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class RestobarApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestobarApplication.class, args);
	}

}
