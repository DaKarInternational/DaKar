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

    @Override
    public Mono<Journey> findByCountryNameWithJPA(String countryName) {
        insertSomeJourneys();
        return journeyRepository.findFirstByCountry(countryName)
                .map(it -> {log.debug(it.toString());return it;});
    }

    @Override
    public Mono<Journey> findByDestinationWithMongoRepo(String destination) {
        insertSomeJourneys();
        return this.journeyRepository.findFirstByDestination(destination);
    }

    @Override
    public List<Journey> allJourney() {
        insertSomeJourneys();
        // http://javasampleapproach.com/reactive-programming/reactor/reactor-convert-flux-into-list-map-reactive-programming
        return this.journeyRepository.findAll()
                .collectList()
                .block();
    }

    @Override
    public Flux<Journey> allJourneyAsFlux() {
        insertSomeJourneys();
        // http://javasampleapproach.com/reactive-programming/reactor/reactor-convert-flux-into-list-map-reactive-programming
        return this.journeyRepository.findAll();
    }

    @Override
    public Mono<Journey> saveJourney(Mono<Journey> journey) {
        return this.journeyRepository.save(journey.block());
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

