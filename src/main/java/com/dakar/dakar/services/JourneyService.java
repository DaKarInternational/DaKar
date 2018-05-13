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

    //useless
    public Mono<Journey> findByCountryNameWithJPA(String countryName) {
        return journeyRepository.findFirstByCountry(countryName)
                .map(it -> {log.debug(it.toString());return it;});
    }

    public Mono<Journey> findByDestinationWithMongoRepo(String destination) {
        return this.journeyRepository.findFirstByDestination(destination);
    }

    public Mono<Journey> insertNewJourney(Journey journey) {
        //TODO : business checks before insert
        return this.journeyRepository.insert(journey);
    }

    public List<Journey> allJourney() {
        // http://javasampleapproach.com/reactive-programming/reactor/reactor-convert-flux-into-list-map-reactive-programming
        return this.journeyRepository.findAll()
                .collectList()
                .block();
    }
}

