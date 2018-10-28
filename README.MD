#Docker setup :  
sudo systemctl start docker  
sudo usermod -a -G docker $USER  
sudo reboot  

add mongo-dakar in your /etc/hosts -> this is temporary. TODO : properties file outside the jar ? or develop in docker ?  
mvnw package  
mvnw dockerfile:build  
docker-compose up  


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

Couchbase:  
http://localhost:8091/  

chmod +x graphiql-app-0.7.2-x86_64.AppImage



docker-compose ps  
docker ps --all

docker build -f Dockerfile.testdb . -t testdb
docker images

docker-compose up -d couch // to launch only the db


To launch the integration tests, either you launch a new container with a couchbase and do all that : 
create cluster   
create bucket  
create index : 

CREATE PRIMARY INDEX ON `test`  

create user 'test' with password = 'password'  

Or you just :  

docker-compose up -d couchtest  

# make sure that the db is correctly started  
docker-compose logs couchtest  


Javadoc:  

./mvnw javadoc:javadoc