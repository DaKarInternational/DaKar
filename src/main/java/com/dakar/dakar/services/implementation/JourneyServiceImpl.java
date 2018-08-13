package com.dakar.dakar.services.implementation;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.repositories.JourneyRepository;
import com.dakar.dakar.services.interfaces.IJourneyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class JourneyServiceImpl implements IJourneyService {

    @Autowired
    private JourneyRepository journeyRepository;

    //useless
    @Override
    public Mono<Journey> findByDestinationWithJPA(String destination) {
//        journeyRepository.findAll().subscribe(journey -> {log.error(journey.toString());});
        fillDbWithDumbData();
        return journeyRepository.findFirstByDestination(destination)
                .map(it -> {
                    log.debug(it.toString());
                    return it;
                });
    }

    @Override
    public Mono<Journey> findByDestinationWithMongoRepo(String destination) {
        return this.journeyRepository.findFirstByDestination(destination);
    }

    @Override
    public Flux<Journey> allJourney() {
        // http://javasampleapproach.com/reactive-programming/reactor/reactor-convert-flux-into-list-map-reactive-programming
        return this.journeyRepository.findAll();
    }

    public Mono<Journey> findByCountry(String country) {
        //TODO : business checks before insert
        return journeyRepository.findFirstByDestination(country);
    }
    /**
     * Just for debugging purpose
     * need to be removed and replaced by integration tests
     */
    public void fillDbWithDumbData() {
        Flux<Journey> flux = Flux.just(
                new Journey("Jack", "afghanistan"),
                new Journey("Chloe", "afghanistan"),
                new Journey("afghanistan", "afghanistan"),
                new Journey("David", "afghanistan"),
                new Journey("Michelle", "afghanistan"));
        journeyRepository
                .saveAll(flux)
                .subscribe(journey -> {log.error(journey.toString());});
    }

    @Override
    public Flux<Journey> saveJourney(Mono<Journey> journey) {
        return journeyRepository.saveAll(journey);
    }
}

