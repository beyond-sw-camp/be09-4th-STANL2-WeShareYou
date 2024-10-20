# Dockerfile for Backend, Database, and Redis setup

# Stage 1: Build Spring Boot backend
FROM gradle:7.6.1-jdk17-alpine AS build
WORKDIR /app
COPY . .
RUN gradle clean build --no-daemon

# Stage 2: Prepare runtime for backend
FROM openjdk:17-alpine
COPY --from=build /app/build/libs/*.jar ./
RUN mv $(ls *.jar | grep -v plain) app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# MongoDB setup
FROM mongo:6.0 AS mongo
EXPOSE 27017

# MariaDB setup
FROM mariadb:10.6 AS mariadb
ENV MYSQL_ROOT_PASSWORD=test
ENV MYSQL_DATABASE=wsu
EXPOSE 3306

# Redis setup
FROM redis:alpine AS redis
EXPOSE 6379
