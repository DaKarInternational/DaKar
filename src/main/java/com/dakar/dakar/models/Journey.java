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

    private String destination;
    private String price;
    private String owner;

    //TODO : builder pattern
    public Journey(String destination, String price) {
        this.destination = destination;
        this.price = price;
    }
}