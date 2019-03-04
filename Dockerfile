#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:8-jre-alpine
COPY --from=build /home/app/target/technologyradar-1.0.jar /usr/local/lib/technologyradar.jar
EXPOSE 8081
ENTRYPOINT ["java", "-Dspring.profiles.active=production", "-jar","/usr/local/lib/technologyradar.jar"]
