package com.dakar.dakar.Controller;

import com.dakar.dakar.Models.Journey;
import com.dakar.dakar.Services.JourneyService;
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
    private JourneyService journeyService;

    @Bean
    RouterFunction<?> routes() {
        return route(RequestPredicates.GET("/test1/{countryName}"), request ->
                ok().body(journeyService.findByCountryNameWithJPA(request.pathVariable("countryName")), Journey.class));
    }

}
