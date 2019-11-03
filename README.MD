#### Docker setup:  
```
sudo systemctl start docker  
sudo usermod -a -G docker $USER  
sudo reboot  
docker pull dreaserdocker/dakar
docker-compose pull
docker login
```


#### On way to launch the app in local: 
1. build jar  
`mvnw install -DskipTests  `

2. build docker  
`mvnw dockerfile:build  `

3. launch test db  
`docker-compose up -d couchtest  `

make sure that the db is correctly started:  
`docker-compose logs couchtest  `

4.launch app  
`mvnw spring-boot:run -Dspring.profiles.active=local`  
or  
`java -jar -Dspring.profiles.active=local dakar-0.0.3.jar`



#### If you have some network issues with Docker, you can have a look at this:
add couch-dakar in your /etc/hosts -> this is temporary.  


#### Launch all docker-compose services
`docker-compose up  `  

#### Some test endpoints
Functional controller   
http://localhost:8080/test5/  

Functional controller :  
http://localhost:8080/test1/afghanistan

Functional controller -> hateoas w/ assembler:  
http://localhost:8080/test2/afghanistan

Functional controller -> hatoas w/ generics:  
http://localhost:8080/test3/afghanistan

Functional controller -> GraphQL:  
http://localhost:8080/graphql

Functional controller -> i18n:  
http://dakar.lambla.eu:8080/welcome/fr/message
http://localhost:8080/welcome/fr/message

Couchbase:  
http://localhost:8091/  




#### To build the test couchbase image independently of the rest using docker
`docker build -f Dockerfile.testdb . -t testdb`  

#### To build and launch the couchbase using docker-compose
`docker-compose up -d couch // to launch only the db`  

### To build and launch only the app without dependencies  
`docker-compose -f docker-compose.yml up -d --no-deps --build app`

#### List all images
`docker images`  


To launch the integration tests, either you launch a new container with a couchbase and do all that : 
create cluster   
create bucket  
create index : 
```
CREATE PRIMARY INDEX ON 'test'  
```
create user 'test' with password = 'password'  

Or just run the test db:  
```
docker-compose up -d couchtest  
```

### to stop only one service
```
docker-compose stop couchtest  
```

### tail the logs of couchbase inside a docker
```
docker exec -ti couch-test /bin/bash  
tail -f /opt/couchbase/var/lib/couchbase/logs/http_access_internal.log  
```
or using docker-compose:  
```
docker-compose -f docker-compose-prod.yml exec couch bash
```

### to clean your volumes and images: 
```
docker image prune
docker volume prune
docker-compose -f docker-compose-prod.yml rm -v
```

# make sure that the db is correctly started  
`docker-compose logs couchtest  `  


#### Generate the javadoc:  

`./mvnw javadoc:javadoc`  

#### List all containers
```
docker-compose ps  
docker ps --all
```

#### Delete all containers
`docker rm $(docker ps -a -q)`  

#### Delete all images
`docker rmi $(docker images -q)`  




#### Some GraphQl queries
```
// all jouneys  
query {
    allJourney{
        destination
    }
}

// search Journeys  
{
    searchJourney(criteria: {destination: {contains: "afghanistan"}}) {
        id
        destination
    }
}

// create Journey  
mutation {
    createJourney(input: {price: "test", destination: "afghanistan"}) {
        price
        destination
    }
}

// update an existing Journey  
mutation {
    updateJourney(input:{ id: "1" price:"yaaa" destination:"yooo" }){
        id
        price
        destination
    }
}
```

