FROM openjdk:8-jdk-alpine

LABEL maintainer="sbanegas92@gmail.com"

VOLUME /tmp

EXPOSE 8084

ARG JAR_FILE=japan-service/build/libs/japan-service-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} japan-service.jar

ENTRYPOINT ["java","-jar","/japan-service.jar"]
