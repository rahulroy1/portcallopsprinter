FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD

# Add Maintainer Info
LABEL maintainer="rahul.roy@in.ibm.com"

# Build the Sprintboot app
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn clean package -DskipTests

# Start with a base image containing Java runtime (mine java 8)
FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/dischargelistprinter-0.0.1-SNAPSHOT.jar /app/

# Run the jar file 
ENTRYPOINT ["java","-Dserver.port=8082", "-Dapp.keystore=/usr/local/openjdk-8/jre/lib/security/cacerts","dischargelistprinter-0.0.1-SNAPSHOT.jar"]
