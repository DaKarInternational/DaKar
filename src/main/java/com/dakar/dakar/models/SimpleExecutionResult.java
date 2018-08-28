package com.dakar.dakar.models;

import graphql.ExecutionResult;
import graphql.GraphQLError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class SimpleExecutionResult implements ExecutionResult {

    private final Object data;
    private final List<GraphQLError> errors;
    private final transient boolean dataPresent;
    private final transient Map<Object, Object> extensions;

    @Override
    public <T> T getData() {
        return (T) this.data;
    }

    @Override
    public List<GraphQLError> getErrors() {
        return null;
    }

    @Override
    public Map<Object, Object> getExtensions() {
        return null;
    }

    @Override
    public Map<String, Object> toSpecification() {
        return null;
    }
}
