package com.dakar.dakar.models;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import lombok.*;
import org.springframework.data.couchbase.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Document
@Data
public class Journey {

    @Id
    private int id;
    @Field
    private String country;
    @Field
    private String price;
    @Field
    private String destination;
    @Field
    private String owner;

    //TODO : builder pattern
    public Journey(String country, String price, String destination) {
        this.country = country;
        this.price = price;
        this.destination = destination;
    }
}