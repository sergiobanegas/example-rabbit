FROM openjdk:8-jdk-alpine

LABEL maintainer="sbanegas92@gmail.com"

VOLUME /tmp

EXPOSE 8085

ARG JAR_FILE=eu-service/build/libs/eu-service-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} eu-service.jar

ENTRYPOINT ["java","-jar","/eu-service.jar"]
