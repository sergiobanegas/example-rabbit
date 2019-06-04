FROM openjdk:8-jdk-alpine

LABEL maintainer="sbanegas92@gmail.com"

VOLUME /tmp

EXPOSE 8081

ARG JAR_FILE=message-engine/build/libs/message-engine-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} message-engine.jar

ENTRYPOINT ["java","-jar","/message-engine.jar"]
