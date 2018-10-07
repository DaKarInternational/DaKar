package com.dakar.dakar.validator;

import com.coxautodev.graphql.tools.SchemaParser;
import com.dakar.dakar.models.GraphQLParameter;
import com.dakar.dakar.resolvers.JourneyResolver;
import com.dakar.dakar.resolvers.MutationResolver;
import com.dakar.dakar.resolvers.QueryResolver;
import com.dakar.dakar.services.interfaces.IJourneyService;
import graphql.language.Document;
import graphql.parser.Parser;
import graphql.schema.GraphQLSchema;
import graphql.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GraphQLValidator {

    @Autowired
    private IJourneyService journeyService;

    public List<ValidationError> validateGraphQL(GraphQLParameter graphQLParameter){
        GraphQLSchema graphQLSchema = SchemaParser.newParser()
                .file("graphQLSchemas/journey.graphqls")
                .resolvers(new QueryResolver(journeyService), new JourneyResolver(), new MutationResolver(journeyService))
                .build().makeExecutableSchema();
        Document document = new Parser().parseDocument(graphQLParameter.getQuery());
        List<ValidationError> errors = new graphql.validation.Validator().validateDocument(graphQLSchema, document);
        return errors;
    }
}
