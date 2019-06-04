FROM openjdk:8-jdk-alpine

LABEL maintainer="sbanegas92@gmail.com"

VOLUME /tmp

EXPOSE 8083

ARG JAR_FILE=italy-service/build/libs/italy-service-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} italy-service.jar

ENTRYPOINT ["java","-jar","/italy-service.jar"]
