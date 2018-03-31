package com.dakar.dakar.Resources;

import com.dakar.dakar.Models.Journey;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Setter
public class JourneyResource extends ResourceSupport {

    @JsonUnwrapped
    private Journey journey;

}
