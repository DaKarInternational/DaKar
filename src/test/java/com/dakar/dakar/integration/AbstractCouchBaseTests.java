package com.dakar.dakar.integration;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.bucket.BucketType;
import com.couchbase.client.java.cluster.ClusterInfo;
import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.cluster.DefaultBucketSettings;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.couchbase.config.CouchbaseConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.couchbase.CouchbaseContainer;

import javax.annotation.PostConstruct;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AbstractCouchBaseTests.CouchbaseTestConfig.class})
@SpringBootTest
public abstract class AbstractCouchBaseTests {

    private static final String clusterUser = "Administrator";
    private static final String clusterPassword = "password";

    @ClassRule
    public static CouchbaseContainer couchbaseContainer = new CouchbaseContainer()
            .withNewBucket(DefaultBucketSettings.builder()
                    .enableFlush(true)
                    .name("test")
                    .password("password")
                    .quota(100)
                    .type(BucketType.COUCHBASE)
                    .build());

    @Configuration
    @Primary
    @ComponentScan(basePackages = "com.dakar.dakar")
    static class CouchbaseTestConfig implements CouchbaseConfigurer {

        private CouchbaseContainer couchbaseContainer;

        @PostConstruct
        public void init() throws Exception {
            couchbaseContainer = AbstractCouchBaseTests.couchbaseContainer;
        }

        @Override
        @Bean
        public CouchbaseEnvironment couchbaseEnvironment() {
            return couchbaseContainer.getCouchbaseEnvironment();
        }

        @Override
        @Bean
        public Cluster couchbaseCluster() throws Exception {
            return couchbaseContainer.getCouchbaseCluster();
        }

        @Override
        @Bean
        public ClusterInfo couchbaseClusterInfo() throws Exception {
            Cluster cc = couchbaseCluster();
            ClusterManager manager = cc.clusterManager(clusterUser, clusterPassword);
            return manager.info();
        }

        @Override
        @Bean
        public Bucket couchbaseClient() throws Exception {
            return couchbaseContainer.getCouchbaseCluster().openBucket("test");
        }
    }
}
