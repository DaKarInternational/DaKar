package com.dakar.dakar.resources;

import com.dakar.dakar.models.Journey;
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
