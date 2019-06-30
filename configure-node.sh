#!/bin/bash
# based on : https://github.com/arun-gupta/docker-images/blob/master/couchbase/configure-node.sh

set -x # Print commands and their arguments as they are executed.
set -m # Job control is enabled.


/entrypoint.sh couchbase-server &

sleep 60

# Setup index and memory quota
curl -v -X POST http://127.0.0.1:8091/pools/default -d memoryQuota=300 -d indexMemoryQuota=300

# Setup services
curl -v http://127.0.0.1:8091/node/controller/setupServices -d services=kv%2Cn1ql%2Cindex

# Setup credentials
curl -v http://127.0.0.1:8091/settings/web -d port=8091 -d username=Administrator -d password=password

# Setup Memory Optimized Indexes
curl -i -u Administrator:password -X POST http://127.0.0.1:8091/settings/indexes -d 'storageMode=memory_optimized'

# Create a bucket 
curl -i -u Administrator:password -X POST http://127.0.0.1:8091/pools/default/buckets -d 'authType=sasl' -d 'saslPassword=password' -d name=test -d ramQuotaMB=200

# Create new "test" user with cluster_admin rights
# https://developer.couchbase.com/documentation/server/current/rest-api/rbac.html
curl -X PUT --data "name=John Smith&roles=cluster_admin&password=password" -H "Content-Type: application/x-www-form-urlencoded" http://Administrator:password@127.0.0.1:8091/settings/rbac/users/local/test

sleep 10

curl -X PUT --data "name=John Smith&roles=cluster_admin&password=password" -H "Content-Type: application/x-www-form-urlencoded" http://Administrator:password@127.0.0.1:8091/settings/rbac/users/local/test

# Create primary index, only if Spring data doesn't do it for us
#curl -v -u Administrator:password http://127.0.0.1:8093/query/service -d 'statement=CREATE PRIMARY INDEX ON `test`'

fg
