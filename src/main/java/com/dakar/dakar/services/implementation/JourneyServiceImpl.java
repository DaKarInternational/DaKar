package com.dakar.dakar.services.implementation;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.models.JourneyCriteriaInput;
import com.dakar.dakar.models.StringFilterCriteriaInput;
import com.dakar.dakar.repositories.IJourneyRepository;
import com.dakar.dakar.services.interfaces.IJourneyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
public class JourneyServiceImpl implements IJourneyService {

    @Autowired
    private IJourneyRepository journeyRepository;

    @Override
    public Flux<Journey> findByDestination(String destination) {
//        journeyRepository.findAll().subscribe(journey -> {log.error(journey.toString());});
        //TODO : business checks before insert
        return journeyRepository.findFirstByDestination(destination);
    }

    /**
     * we should never do this kind of requests
     * @return Flux of all Journeys
     */
    @Override
    public Flux<Journey> allJourney() {
        // http://javasampleapproach.com/reactive-programming/reactor/reactor-convert-flux-into-list-map-reactive-programming
        return this.journeyRepository.findAll();
    }

    /**
     * Just for debugging purpose
     * need to be removed and replaced by integration tests
     */
/*    public void fillDbWithDumbData() {
        Flux<Journey> flux = Flux.just(
                new Journey("Jack", "afghanistan"),
                new Journey("Chloe", "afghanistan"),
                new Journey("afghanistan", "afghanistan"),
                new Journey("David", "afghanistan"),
                new Journey("Michelle", "afghanistan"));
        journeyRepository
                .saveAll(flux)
                .subscribe(journey -> {log.error(journey.toString());});
    }*/

    @Override
    public Flux<Journey> saveJourney(Mono<Journey> journey) {
        return journeyRepository.saveAll(journey);
    }

    @Override
    public Mono<Void> deleteJourney(String id) {
        Mono<Void> mono = journeyRepository.deleteById(id);
        return mono;
    }

    @Override
    public Mono<Journey> findById(String id) {
        // http://javasampleapproach.com/reactive-programming/reactor/reactor-convert-flux-into-list-map-reactive-programming
        return this.journeyRepository.findById(id);
    }

    /**
     * Search by criterias 
     * @param criterias like destination, price, etc...
     * @return Flux of Journeys found
     */
    @Override
    public Flux<Journey> findByCriterias(JourneyCriteriaInput criterias) {
        // https://www.baeldung.com/java-optional
        // https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html#of-T-
        Optional<String> destination = Optional.ofNullable(criterias)
                .map(JourneyCriteriaInput::getDestination)
                .map(StringFilterCriteriaInput::getContains)
                .filter(StringUtils::hasText);
        Optional<String> price = Optional.ofNullable(criterias)
                .map(JourneyCriteriaInput::getPrice)
                .map(StringFilterCriteriaInput::getContains)
                .filter(StringUtils::hasText);
        if (destination.isPresent() && price.isPresent()) {
            //TODO: we need to 'curify' this so that we can compose functions and remove the if
            return journeyRepository.findByDestinationAndPrice(destination.get(), price.get());
        } else if (destination.isPresent()) {
            return journeyRepository.findByDestination(destination.get());
        } else if (price.isPresent()) {
            return journeyRepository.findByPrice(price.get());
        } else {
            return journeyRepository.findAll();
        }
    }
}