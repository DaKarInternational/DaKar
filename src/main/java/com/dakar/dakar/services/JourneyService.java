package com.dakar.dakar.services;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.repositories.JourneyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    public Flux<Journey> insertNewJourneyMongo(Mono<Journey> journey) {
        //TODO : business checks before insert
        return this.journeyRepository.saveAll(journey);
    }

    public Flux<Journey> allJourney() {
        // http://javasampleapproach.com/reactive-programming/reactor/reactor-convert-flux-into-list-map-reactive-programming
        return this.journeyRepository.findAll();
    }

    public Mono<Journey> insertNewJourneyInCouchbase(Mono<Journey> journey) {
        //TODO : business checks before insert
        this.journeyRepository.saveAll(journey).subscribe((it) -> log.debug(it.toString()), (it) -> log.debug(it.toString()), () -> log.debug("done"));
        //TODO: Need to fetch the new journey with the Id
        return journey;
    }

    public Mono<Journey> findByCountry(String country) {
        //TODO : business checks before insert
        return this.journeyRepository.findFirstByCountry(country);
    }
    /**
     * Just for debugging purpose
     * need to be removed and replaced by integration tests
     */
    public void fillDbWithDumbData() {
        Flux<Journey> flux = Flux.just(
                new Journey("Jack", "Bauer", "afghanistan"),
                new Journey("Chloe", "O'Brian", "afghanistan"),
                new Journey("afghanistan", "Bauer", "afghanistan"),
                new Journey("David", "Palmer", "afghanistan"),
                new Journey("Michelle", "Dessler", "afghanistan"));
        journeyRepository
                .saveAll(flux)
                .subscribe();
    }
}

