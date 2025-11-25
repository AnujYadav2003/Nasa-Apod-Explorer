package com.nasa.nasaapod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class NasaapodApplication {

	public static void main(String[] args) {
		SpringApplication.run(NasaapodApplication.class, args);
	}

}
