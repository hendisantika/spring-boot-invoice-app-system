#FROM adoptopenjdk/openjdk11:alpine-jre
#FROM openjdk:21-slim
#FROM bellsoft/liberica-openjdk-debian:21
FROM amazoncorretto:21-alpine-jdk
LABEL maintainer="hendisantika@yahoo.co.id"
MAINTAINER Hendi Santika "hendisantika@yahoo.co.id"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/application.jar"]
