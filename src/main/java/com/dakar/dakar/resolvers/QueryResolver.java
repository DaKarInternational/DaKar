package com.dakar.dakar.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dakar.dakar.models.Journey;
import com.dakar.dakar.models.JourneyCriteriaInput;
import com.dakar.dakar.services.interfaces.IJourneyService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *
 * We can't use autowired here with this https://github.com/graphql-java/graphql-spring-boot because
 * it does not support webflux yet
 * and https://github.com/oembedler/spring-graphql-common#requires
 * is outdated
 */
@Slf4j
public class QueryResolver implements GraphQLQueryResolver {

    // no autowire possible here yet
    private IJourneyService journeyService;

    //instead we use the old fashion
    public QueryResolver(IJourneyService journeyService) {
        this.journeyService = journeyService;
    }

    /**
     * Here we'll gonna call our proper service, where we can use Autowired as we want
     * @return see if we can return a Flux there ?
     * 
     * for the Flux we will wait for this : https://github.com/graphql-java/graphql-java-tools/issues/103
     */
    public List<Journey> allJourney() {
        return journeyService.allJourney().collectList().block();
    }

    /**
     *
     * @param id searched
     * @return Journey that match the id
     */
    public Journey findJourneyById(String id){
        Journey block = null;
        try {
            block = journeyService.findById(id)
                    .block();
        } catch (Exception e) {
            log.error("An error occurred during the research of the JourneyID: " + id);
        }
        return block;
    }

    /**
     * @param criterias searched
     * @return list of Journeys that match the criterias
     */
    public List<Journey> searchJourney(JourneyCriteriaInput criterias) {
        List<Journey> block = null;
        try {
            block = journeyService.findByCriterias(criterias)
                    .collectList()
                    .block();
        } catch (Exception e) {
            log.error("An error occurred during the research with these criterias: " + criterias);
        }
        return block;

    }
}