package com.dakar.dakar.services;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.repositories.JourneyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class JourneyService {

    @Autowired
    private JourneyRepository journeyRepository;

    public Mono<Journey> findByCountryNameWithJPA(String countryName) {
        insertSomeJourneys();
        return Mono.just(journeyRepository.findFirstByCountry(countryName))
                .map(it -> {log.debug(it.toString());return it;});
    }

    public Mono<Journey> findByDestinationWithMongoRepo(String destination) {
        insertSomeJourneys();
        return Mono.just(this.journeyRepository.findFirstByDestination(destination));
    }

    public List<Journey> allJourney() {
        insertSomeJourneys();
        return this.journeyRepository.findAll();
    }

    /**
     * save a couple of Journey in H2DB
     */
    private void insertSomeJourneys() {
        journeyRepository.save(new Journey("Jack", "Bauer","afghanistan"));
        journeyRepository.save(new Journey("Chloe", "O'Brian", "afghanistan"));
        journeyRepository.save(new Journey("afghanistan", "Bauer","afghanistan"));
        journeyRepository.save(new Journey("David", "Palmer","afghanistan"));
        journeyRepository.save(new Journey("Michelle", "Dessler","afghanistan"));
    }
}

