FROM maven:3.6.3-jdk-8-alpine AS MAVEN_BUILD

# Add Maintainer Info
LABEL maintainer="rahul.roy@in.ibm.com"

# Build the Sprintboot app
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package