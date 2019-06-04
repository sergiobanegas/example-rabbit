FROM openjdk:8-jdk-alpine

LABEL maintainer="sbanegas92@gmail.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=message-manager/build/libs/message-manager-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} message-manager.jar

ENTRYPOINT ["java","-jar","/message-manager.jar"]
