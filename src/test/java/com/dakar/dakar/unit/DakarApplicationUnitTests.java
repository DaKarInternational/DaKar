package com.dakar.dakar.unit;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.repositories.JourneyRepository;
import com.dakar.dakar.services.JourneyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class DakarApplicationUnitTests {

    @InjectMocks
    private JourneyService journeyService;

    @Mock
    private JourneyRepository journeyRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    //stupid test
    public void gotAllJourney() {
        when(journeyRepository.findAll()).thenReturn(Flux.just(new Journey()));

        List<Journey> journeyList = journeyService.allJourney().collectList().block();
        assertNotNull(journeyList);
    }

    @Test
    public void insertJourney() {
        when(journeyRepository.saveAll((Publisher<Journey>) any())).thenReturn(Flux.just(new Journey("", "", "")));

        Mono<Journey> journeyMono = Mono.just(new Journey("", "", ""));
        Mono<Journey> journeyInserted = journeyService.insertNewJourneyInCouchbase(journeyMono);
        //TODO : check the business rules instead of just the values
        assertNotNull(journeyInserted);
    }
}
