package com.dakar.dakar.resolvers;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;
import com.dakar.dakar.controller.MyPublisher;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;

@Slf4j
public class SubscriptionResolver implements GraphQLSubscriptionResolver {

    private MyPublisher publisher = new MyPublisher();

    Publisher<Integer> stockQuotes(String stockCodes) {
        log.info("Security context principal: {}");
        return publisher;
    }
}
