FROM openjdk:8-jdk-alpine

LABEL maintainer="sbanegas92@gmail.com"

VOLUME /tmp

EXPOSE 8082

ARG JAR_FILE=spain-service/build/libs/spain-service-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} spain-service.jar

ENTRYPOINT ["java","-jar","/spain-service.jar"]
