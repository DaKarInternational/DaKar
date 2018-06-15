package com.dakar.dakar.controller;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.services.interfaces.IJourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Controller
public class FunctionnalController {

    @Autowired
    private IJourneyService journeyService;

    @Bean
    RouterFunction<?> routes() {
        return route(RequestPredicates.GET("/test1/{countryName}"), request ->
                ok().body(journeyService.findByCountryNameWithJPA(request.pathVariable("countryName")), Journey.class));
    }

}
