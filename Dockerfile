#https://docs.docker.com/engine/reference/builder/#using-arg-variables
FROM openjdk:8-jdk-alpine
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]