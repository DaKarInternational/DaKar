package com.dakar.dakar.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Document
public class Journey {

    private String country;
    private String price;
    private String destination;
    private String owner;

    //TODO : builder pattern
    public Journey(String country, String price, String destination) {
        this.country = country;
        this.price = price;
        this.destination = destination;
    }
}