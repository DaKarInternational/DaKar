package com.dakar.dakar.acceptance;

import com.couchbase.client.java.bucket.BucketType;
import com.couchbase.client.java.cluster.DefaultBucketSettings;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.couchbase.CouchbaseContainer;
import org.testcontainers.shaded.javax.inject.Singleton;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
@ContextConfiguration(classes = {ConfigTest.class})
//@ComponentScan(basePackages = "com.dakar.dakar")
//@Configuration
public abstract class AbstractCouchBaseTests {

    @ClassRule
    public static CouchbaseContainer couchbaseContainer = new CouchbaseContainer()
            .withNewBucket(DefaultBucketSettings.builder()
                    .enableFlush(true)
                    .name("test")
                    .password("password")
                    .quota(100)
                    .type(BucketType.COUCHBASE)
                    .build());

}
