package com.dakar.dakar.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
@NoArgsConstructor
public class GraphQLParameter {
    String query;
    String operationName;
    HashMap<String, Object> variables;
}
    
