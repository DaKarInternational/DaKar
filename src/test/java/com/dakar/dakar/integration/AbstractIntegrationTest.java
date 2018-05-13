package com.dakar.dakar.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
@SpringBootTest
public abstract class AbstractIntegrationTest {

    @ClassRule
    public static GenericContainer mongo = new GenericContainer("mongo:latest").withExposedPorts(27017);

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of("spring.data.mongodb.uri=mongodb://" + mongo.getContainerIpAddress() + ":" + mongo.getMappedPort(27017) + "/mongo-dakar").applyTo(configurableApplicationContext);
            log.debug("New property : " + configurableApplicationContext.getEnvironment().getProperty("spring.data.mongodb.uri"));//=mongodb://" + mongo.getContainerIpAddress() + ":" + mongo.getMappedPort(27017) + "/mongo-dakar");
        }
    }
}
