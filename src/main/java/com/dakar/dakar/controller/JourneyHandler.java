package com.dakar.dakar.controller;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.services.interfaces.IJourneyService;
import com.dakar.dakar.validator.JourneyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.print.Book;
import java.net.URI;

/**
 * Handler for requests Journey
 */
@Component
public class JourneyHandler {

    @Autowired
    private RequestHandler requestHandler;

    @Autowired
    private IJourneyService journeyService;

    public Mono<ServerResponse> saveJourney(ServerRequest request) {

        return requestHandler.requireValidBody(
                body -> {
                    Mono<Journey> journeyMono = (Mono<Journey>)body;
                    return ServerResponse.ok().body(journeyService.saveJourney(journeyMono), Journey.class);
                }, request, Journey.class);

    }
}
