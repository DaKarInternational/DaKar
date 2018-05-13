package com.dakar.dakar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class DakarApplication {

	public static void main(String[] args) {
		SpringApplication.run(DakarApplication.class, args);
	}
}
