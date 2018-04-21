#Docker setup :
sudo systemctl start docker
sudo usermod -a -G docker $USER
sudo reboot

add mongo-dakar in your /etc/hosts -> this is temporary. TODO : properties file outside the jar ? or develop in docker ?
mvnw package
mvnw dockerfile:build
docker-compose up


Functional controller :
http://localhost:8080/test1/afghanistan

Annotation controller -> hateoas w/ assembler:
http://localhost:8080/test2/afghanistan

Annotation controller -> hatoas w/ generics:
http://localhost:8080/test3/afghanistan

Annotation controller -> GraphQL:
http://localhost:8080/graphql


