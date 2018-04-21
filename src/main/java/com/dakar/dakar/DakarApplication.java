package com.dakar.dakar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
@SpringBootApplication
public class DakarApplication {

	public static void main(String[] args) {
		SpringApplication.run(DakarApplication.class, args);
	}
}
