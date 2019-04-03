package com.dakar.dakar.acceptance.steps;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonSteps {

    // To parse result as json
    public static final Gson GSON = new Gson();

    // Journey requests
    protected static final String REQUEST_ALL_JOURNEY = "allJourney";
    protected static final String REQUEST_CREATE_JOURNEY = "createJourney";
    protected static final String REQUEST_FIND_JOURNEY_BY_ID = "findJourneyById";
    protected static final String REQUEST_SEARCH_JOURNEY_BY_CRITERIAS = "searchJourney";
}
