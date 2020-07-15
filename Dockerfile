FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD

# Add Maintainer Info
LABEL maintainer="rahul.roy@in.ibm.com"

# Build the Sprintboot app
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package

# Start with a base image containing Java runtime (mine java 8)
FROM openjdk:8-jdk-alpine

WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/dischargelistprinter-0.0.1-SNAPSHOT.jar /app/

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/dischargelistprinter-0.0.1-SNAPSHOT.jar"]