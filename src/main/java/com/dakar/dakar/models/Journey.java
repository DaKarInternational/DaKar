package com.dakar.dakar.models;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import lombok.*;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;

import static org.springframework.data.couchbase.core.mapping.id.GenerationStrategy.UNIQUE;

    //TODO : builder pattern or static factory
@Data
@Document
public class Journey {

    //https://docs.spring.io/spring-data/couchbase/docs/current/reference/html/#couchbase.autokeygeneration.unique
    @Id @GeneratedValue(strategy = UNIQUE)
    private String id;
    @NonNull
    @Field
    private String price;
    @NonNull
    @Field
    private String destination;
    @Field
    private String owner;

}