package com.dakar.dakar.Services;

import com.dakar.dakar.Models.Journey;
import com.dakar.dakar.Repositories.JourneyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class JourneyService {

    @Autowired
    private JourneyRepository journeyRepository;

    public Mono<Journey> findByCountryNameWithJPA(String countryName) {

        // save a couple of Journey in H2DB
        journeyRepository.save(new Journey("Jack", "Bauer"));
        journeyRepository.save(new Journey("Chloe", "O'Brian"));
        journeyRepository.save(new Journey("afghanistan", "Bauer"));
        journeyRepository.save(new Journey("David", "Palmer"));
        journeyRepository.save(new Journey("Michelle", "Dessler"));

        return Mono.just(journeyRepository.findFirstByCountry(countryName))
                .map(it -> {log.debug(it.toString());return it;});
    }

    public Mono<Journey> findByIdWithJPA(String id) {

        // save a couple of Journey in H2DB
        journeyRepository.save(new Journey("Jack", "Bauer"));
        journeyRepository.save(new Journey("Chloe", "O'Brian"));
        journeyRepository.save(new Journey("afghanistan", "Bauer"));
        journeyRepository.save(new Journey("David", "Palmer"));
        journeyRepository.save(new Journey("Michelle", "Dessler"));

        return Mono.just(journeyRepository.findFirstById(Integer.parseInt(id)))
                .map(it -> {log.debug(it.toString());return it;});
    }
}

