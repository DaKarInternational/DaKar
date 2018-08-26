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


//    @Override
//    @Bean(name = BeanNames.COUCHBASE_CLUSTER_INFO)
//    public ClusterInfo couchbaseClusterInfo() throws Exception {
//        return couchbaseCluster().clusterManager("Administrator", getBucketPassword()).info();
//    }
//
//    @Bean(
//            destroyMethod = "close",
//            name = {"couchbaseBucket"}
//    )
//    public Bucket couchbaseClient() throws Exception {
//        return this.couchbaseCluster().openBucket(this.getBucketName(), this.getBucketPassword());
//    } 

//    @Bean(
//            name = {"couchbaseCustomConversions"}
//    )
//    public CustomConversions customConversions() {
//        return super.customConversions();
//    }
//
//    
//    @Bean(
//            name = {"couchbaseIndexManager"}
//    )
//    @Override
//    public IndexManager indexManager() {
//        return new IndexManager(true, true, false);
//    }

    @Override
    protected String getBucketName() {
        return "test";
    }

    @Override
    protected String getBucketPassword() {
        return "password";
    }
}
