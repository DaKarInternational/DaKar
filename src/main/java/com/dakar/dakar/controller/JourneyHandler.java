package com.dakar.dakar.controller;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.services.interfaces.IJourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

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
