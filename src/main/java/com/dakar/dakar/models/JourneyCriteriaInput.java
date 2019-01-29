package com.dakar.dakar.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class JourneyCriteriaInput {

    private StringFilterCriteriaInput destination;
    private StringFilterCriteriaInput price;
}
