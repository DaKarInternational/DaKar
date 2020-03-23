# Dakar

Fully reactive online travel agency made with Spring Boot 2 (w/ WebFlux), Angular, GraphQL, CouchBase, Maven, CircleCI, and Docker.

The goal is to learn as much as possible about new tech while creating a small but hopefully helpful travel agency fully made in reactive programming.

## Maven Central Repo
https://mvnrepository.com/artifact/com.dakar/dakar

## Docker hub 
https://hub.docker.com/repository/docker/dakarinternational/dakar

## Github Release
https://github.com/DaKarInternational/DaKar/releases/tag/3.0.0


### On way to launch the app  
1. build jar  
`mvnw clean install -DskipTests`

2. build docker  
`./mvnw dockerfile:build`

3. launch test db  
`docker-compose up -d couchtest`

4. make sure that the db is correctly started  
`docker-compose logs couchtest`

5. launch the app within your IDE  
`mvnw spring-boot:run -Dspring.profiles.active=local`


If you have some network issues with Docker, you may consider having a look at your hosts file  
add couch-dakar in your /etc/hosts 



### Some test endpoints

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

## Limitations
- Swagger with spring webflux
- GraphQL subscriptions with spring webflux

## Some useful commands  

### Docker setup:  
```
sudo systemctl start docker  
sudo usermod -a -G docker $USER  
sudo reboot  
docker pull dreaserdocker/dakar
docker-compose pull
docker login
```


### List all images
`docker images`

### Build the test couchbase image independently of the rest using docker
`docker build -f Dockerfile.testdb . -t testdb`

### Build and launch the couchbase using docker-compose
`docker-compose up -d couch // to launch only the db`

### Launch all docker-compose services
`docker-compose up  `

### To launch the db

#### Either you launch a new container with a couchbase and do all that  

create cluster   
create bucket named 'test'  
create index  

```
CREATE PRIMARY INDEX ON `test`  
```

create user 'test' with password = 'password'  

#### Or you just   
```
docker-compose up -d couchtest  

docker-compose stop couchtest  

docker exec -ti couch-test /bin/bash  
tail -f /opt/couchbase/var/lib/couchbase/logs/http_access_internal.log  
```
#### Or  
```
docker-compose -f docker-compose-prod.yml exec couch bash

docker image prune
docker volume prune
docker-compose -f docker-compose-prod.yml rm -v
```

### Generate the javadoc  
```
./mvnw javadoc:javadoc
```

### Maven deploy
```
./mvnw clean package source:jar javadoc:jar deploy
```

### Maven build and push the docker  
```
./mvnw dockerfile:build dockerfile:push 
```

### List all containers

```
docker-compose ps  
docker ps --all
```

### Delete all containers
```
docker rm $(docker ps -a -q)
```

### Delete all images
```
docker rmi $(docker images -q)
```




### Some examples of GraphQl queries

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

```

