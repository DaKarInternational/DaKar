package com.dakar.dakar.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

// Util class for json parse
public class JsonParser {

    private static final String DATA_KEY = "data";
    private static final String JSON_ARRAY = "JSON_ARRAY";
    private static final String JSON_OBJECT = "JSON_OBJECT";

    public static JsonArray getJsonArray(String jsonInput, String requestName) {
        JsonObject data = getDataObject(jsonInput);
        return data.getAsJsonArray(requestName);
    }

    public static JsonObject getJsonObject(String jsonInput, String requestName) {
        JsonObject data = getDataObject(jsonInput);
        return data.getAsJsonObject(requestName);
    }

    public static JsonObject getDataObject(String jsonInput) {
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(jsonInput, JsonObject.class);
        return json.getAsJsonObject(DATA_KEY);
    }
}
