package com.dakar.dakar;

import com.dakar.dakar.Repositories.JourneyRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RoutesConfiguration {

    @Bean
    RouterFunction<?> routes(JourneyRepository journeyRepository) {
        return route(RequestPredicates.GET("/{id}"), request ->
                ok().body(journeyRepository.findById(request.pathVariable("id")), Integer.class));
    }
}
