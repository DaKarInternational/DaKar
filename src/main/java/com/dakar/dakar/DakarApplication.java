package com.dakar.dakar;

import com.coxautodev.graphql.tools.SchemaParser;
import com.dakar.dakar.resolvers.JourneyResolver;
import com.dakar.dakar.resolvers.MutationResolver;
import com.dakar.dakar.resolvers.QueryResolver;
import com.dakar.dakar.services.JourneyService;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;

@EnableReactiveCouchbaseRepositories
@SpringBootApplication
public class DakarApplication {

    @Autowired
    private JourneyService journeyService;

    public static void main(String[] args) {
		SpringApplication.run(DakarApplication.class, args);
	}

    @Bean
    public GraphQL buildGraphQL() {
        journeyService.fillDbWithDumbData();
        GraphQLSchema graphQLSchema = SchemaParser.newParser()
                .file("graphQLSchemas/journey.graphqls")
                .resolvers(new QueryResolver(journeyService), new JourneyResolver(), new MutationResolver(journeyService))
                .build()
                .makeExecutableSchema();
        return GraphQL.newGraphQL(graphQLSchema).build();
    }
}
