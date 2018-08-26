package com.dakar.dakar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;

import java.util.Arrays;
import java.util.List;

/**
 * https://docs.spring.io/spring-data/couchbase/docs/3.0.8.RELEASE/reference/html/
 * 
 * otherwise you can use the auto-configuration and follow: https://spring.io/blog/2016/04/14/couchbase-as-a-first-class-citizen-of-spring-boot-1-4
 * 
 * We can also load the config from custom properties: https://dzone.com/articles/getting-started-couchbase-and
 */
@Configuration
@EnableReactiveCouchbaseRepositories
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Override
    protected List<String> getBootstrapHosts() {
        return Arrays.asList("localhost");
    }

    @Override
    protected String getBucketName() {
        return "test";
    }

    @Override
    protected String getBucketPassword() {
        return "password";
    }
}
