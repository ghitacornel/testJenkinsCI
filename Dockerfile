#FROM amazoncorretto:11-alpine-jdk
FROM adoptopenjdk/openjdk16
COPY target/app-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]