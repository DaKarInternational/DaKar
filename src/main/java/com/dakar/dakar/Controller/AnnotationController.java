package com.dakar.dakar.Controller;

import com.dakar.dakar.Models.Journey;
import com.dakar.dakar.Services.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AnnotationController {

    @Autowired
    private JourneyService journeyService;

    @RequestMapping("/test2/{id}")
    Mono<Journey> routeWithAnnotation(@PathVariable(value = "id") String id){
        return journeyService.findByIdWithJPA(id);
    }
}
