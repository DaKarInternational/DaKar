package com.dakar.dakar.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class JourneyInput {

    private String id;
    private String destination;
    private String price;
    private String owner;

}
