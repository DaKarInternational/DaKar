package com.dakar.dakar.acceptance;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.bucket.BucketType;
import com.couchbase.client.java.cluster.BucketSettings;
import com.couchbase.client.java.cluster.ClusterInfo;
import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.cluster.DefaultBucketSettings;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.dakar.dakar.acceptance.AbstractCouchBaseTests;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.couchbase.config.CouchbaseConfigurer;
import org.testcontainers.couchbase.CouchbaseContainer;

import javax.annotation.PostConstruct;

@Configuration
@Primary
@ComponentScan(basePackages = "com.dakar.dakar")
public class ConfigTest implements CouchbaseConfigurer {

    private static final String clusterUser = "Administrator";
    private static final String clusterPassword = "password";

    private CouchbaseContainer couchbaseContainer;

    @PostConstruct
    public void init() throws Exception {

        couchbaseContainer = AbstractCouchBaseTests.couchbaseContainer;
        BucketSettings settings = DefaultBucketSettings.builder()
                .enableFlush(true).name("default").quota(100).replicas(0).type(BucketType.COUCHBASE).build();
        settings = couchbaseCluster().clusterManager(clusterUser, clusterPassword).insertBucket(settings);
        couchbaseContainer.callCouchbaseRestAPI("/settings/indexes", "indexerThreads=0&logLevel=info&maxRollbackPoints=5&storageMode=memory_optimized");
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
