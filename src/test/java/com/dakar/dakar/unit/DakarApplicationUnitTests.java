package com.dakar.dakar.unit;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.repositories.JourneyRepository;
import com.dakar.dakar.services.interfaces.IJourneyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class DakarApplicationUnitTests {

    @InjectMocks
    private IJourneyService journeyService;

    @Mock
    private JourneyRepository journeyRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    //stupid test
    public void gotAllJourney() {
        List<Journey> journeyList = journeyService.allJourney();
        assertNotNull(journeyList);
    }

    @Test
    public void insertJourney() {
//        Journey journey = new Journey();
//        Mono<Journey> journeyList = journeyService.insertNewJourney(journey);
//        //TODO : check the business rules instead of just the values
//        assertNotNull(journeyList);
    }
}
