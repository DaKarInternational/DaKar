package com.dakar.dakar.services.implementation;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.repositories.JourneyRepository;
import com.dakar.dakar.services.interfaces.IJourneyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class JourneyServiceImpl implements IJourneyService {

    @Autowired
    private JourneyRepository journeyRepository;

    //useless
    @Override
    public Mono<Journey> findByDestinationWithJPA(String destination) {
        return journeyRepository.findFirstByDestination(destination)
                .map(it -> {log.debug(it.toString());return it;});
    }

    @Override
    public Mono<Journey> findByDestinationWithMongoRepo(String destination) {
        return this.journeyRepository.findFirstByDestination(destination);
    }

    public Journey insertJourney(Journey journey) {
        //TODO : business checks before insert
        return this.journeyRepository.insert(journey)
                .block();
    }

    @Override
    public List<Journey> allJourney() {
        // http://javasampleapproach.com/reactive-programming/reactor/reactor-convert-flux-into-list-map-reactive-programming
        return this.journeyRepository.findAll()
                .collectList()
                .block();
    }

    @Override
    public Flux<Journey> allJourneyAsFlux() {
        // http://javasampleapproach.com/reactive-programming/reactor/reactor-convert-flux-into-list-map-reactive-programming
        return this.journeyRepository.findAll();
    }

    @Override
    public Mono<Journey> saveJourney(Mono<Journey> journey) {
        return this.journeyRepository.save(journey.block());
    }

}

