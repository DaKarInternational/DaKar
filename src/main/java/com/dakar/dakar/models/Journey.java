package com.dakar.dakar.models;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static org.springframework.data.couchbase.core.mapping.id.GenerationStrategy.UNIQUE;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Journey {

    //https://docs.spring.io/spring-data/couchbase/docs/current/reference/html/#couchbase.autokeygeneration.unique
    @Id
    @GeneratedValue(strategy = UNIQUE)
    private String id;
    @Field
    @NotEmpty
    private String price;
    @NonNull
    @Field
    private String destination;
    @Field
    private String owner;

}