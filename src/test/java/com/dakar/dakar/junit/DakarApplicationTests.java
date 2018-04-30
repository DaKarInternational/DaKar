package com.dakar.dakar.junit;

import com.dakar.dakar.Models.Journey;
import com.dakar.dakar.Services.JourneyService;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DakarApplicationTests {

	@Autowired
	private JourneyService journeyService;

	@ClassRule
	public static GenericContainer mongo = new GenericContainer("mongo:latest").withExposedPorts(27017);

	@Test
	public void contextLoads() {
	}

	@Test
	public void accessDockerizedMongoDb() {
		String redisUrl = mongo.getContainerIpAddress() + ":" + mongo.getMappedPort(27017);
		assertTrue(redisUrl.contains("localhost:"));
	}

	@Test
	public void gotAllJourney() {
		// TODO : Spring should use the testContainer MongoDb
		List<Journey> journeyList = journeyService.allJourney();
		assertNotNull(journeyList);
	}
}
