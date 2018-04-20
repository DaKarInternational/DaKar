Functional controller :
http://localhost:8080/test1/afghanistan

Annotation controller -> hateoas w/ assembler:
http://localhost:8080/test2/afghanistan

Annotation controller -> hatoas w/ generics:
http://localhost:8080/test3/afghanistan

Annotation controller -> GraphQL:
http://localhost:8080/graphql


#Docker setup :
sudo systemctl start docker
sudo usermod -a -G docker $USER
sudo reboot
docker run --name mongo-dakar -p 27017:27017 -d mongo
