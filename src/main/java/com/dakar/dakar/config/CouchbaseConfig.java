package com.dakar.dakar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;
import org.springframework.data.couchbase.repository.support.IndexManager;

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

    @Value("${spring.couchbase.bucket.name}")
    private String bucketName;
    @Value("${spring.couchbase.bucket.password}")
    private String password;
    @Value("${spring.couchbase.bootstrap-hosts}")
    private String ip;

    @Override
    protected List<String> getBootstrapHosts() {
        return Arrays.asList(this.ip);
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

    // https://docs.spring.io/spring-data/couchbase/docs/current/reference/html/#couchbase.repository.indexing
    //this is for dev so it is ok to auto-create indexes
    @Override
    public IndexManager indexManager() {
        return new IndexManager(true, true, true);
    }

    @Override
    protected String getBucketName() {
        return this.bucketName;
    }

    @Override
    protected String getBucketPassword() {
        return this.password;
    }
}
