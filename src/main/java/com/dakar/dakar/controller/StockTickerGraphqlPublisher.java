package com.dakar.dakar.controller;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class StockTickerGraphqlPublisher {
    private final static StockTickerPublisher STOCK_TICKER_PUBLISHER = new StockTickerPublisher();

    private DataFetcher stockQuotesSubscriptionFetcher() {
        return environment -> {
            List<String> arg = environment.getArgument("stockCodes");
            List<String> stockCodesFilter = arg == null ? Collections.emptyList() : arg;
            if (stockCodesFilter.isEmpty()) {
//                return STOCK_TICKER_PUBLISHER.getPublisher();
//            } else {
//                return STOCK_TICKER_PUBLISHER.getPublisher(stockCodesFilter);
            }
            return null;
        };
    }

}
