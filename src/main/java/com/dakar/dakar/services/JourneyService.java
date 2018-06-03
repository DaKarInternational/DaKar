package com.dakar.dakar.services;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.repositories.JourneyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Service
@Slf4j
public class JourneyService {

    @Autowired
    private JourneyRepository journeyRepository;

    public Mono<Journey> findByCountryNameWithJPA(String countryName) {
        insertSomeJourneys();
        return journeyRepository.findFirstByCountry(countryName)
                .map(it -> {log.debug(it.toString());return it;});
    }

    public Mono<Journey> findByDestinationWithMongoRepo(String destination) {
        insertSomeJourneys();
        return this.journeyRepository.findFirstByDestination(destination);
    }

    public List<Journey> allJourney() {
        insertSomeJourneys();
        // http://javasampleapproach.com/reactive-programming/reactor/reactor-convert-flux-into-list-map-reactive-programming
        return this.journeyRepository.findAll()
                .collectList()
                .block();
    }

    /**
     * save a couple of Journey in the mongo testContainer
     */
    private void insertSomeJourneys() {
        Flux<Journey> flux = Flux.just(
                new Journey("Jack", "Bauer", "pompei"),
                new Journey("Chloe", "O'Brian", "afghanistan"),
                new Journey("afghanistan", "Bauer", "chicago"),
                new Journey("David", "Palmer", "dubai"),
                new Journey("Michelle", "Dessler", "portugal"));
        journeyRepository
                .insert(flux)
                .subscribe();
    }
}

