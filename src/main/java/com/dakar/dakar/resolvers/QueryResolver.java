package com.dakar.dakar.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dakar.dakar.models.Journey;
import com.dakar.dakar.services.interfaces.IJourneyService;

import java.util.List;

/**
 *
 * We can't use autowired here with this https://github.com/graphql-java/graphql-spring-boot because
 * it does not support webflux yet
 * and https://github.com/oembedler/spring-graphql-common#requires
 * is outdated
 */
public class QueryResolver implements GraphQLQueryResolver {

    // no autowire here
    private IJourneyService journeyService;

    //instead we use the old fashion
    public QueryResolver(IJourneyService journeyService) {
        this.journeyService = journeyService;
    }

    /**
     * Here we'll gonna call our proper service, where we can use Autowire as we want
     * @return see if we can't return a Flux there ?
     */
    public List<Journey> allJourney() {
        return journeyService.allJourney();
    }
}