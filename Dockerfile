

# Use a base image with Java installed_updated
#FROM openjdk:17-oracle

# Set the working directory in the container
#WORKDIR /app
#added new
# Copy the JAR file into the container
#COPY target/sctp-cohort5-m3-grp3-0.0.1-SNAPSHOT.jar /app
# Specify the command to run your application
#CMD ["java", "-jar", "sctp-cohort5-m3-grp3-0.0.1-SNAPSHOT.jar"]

# new added
# Stage 1: Build the application
#FROM openjdk:17-oracle AS build
FROM cimg/openjdk:17.0
WORKDIR /app

# Copy the Maven wrapper files and project description
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Install Wine and set permissions
RUN apt-get update && \
    apt-get install -y wine && \
    chmod +x mvnw

# Copy the OWASP Dependency-Check files
COPY dependency-check-9.1.0-release /app/dependency-check-9.1.0-release

# Set permissions for the run script
RUN chmod +x /app/dependency-check-9.1.0-release/dependency-check/bin/run

# Build the application
RUN ./mvnw dependency:go-offline && \
    ./mvnw install -DskipTests

# Copy the built JAR file
COPY target/sctp-cohort5-m3-grp3-0.0.1-SNAPSHOT.jar /app/sctp-cohort5-m3-grp3-0.0.1-SNAPSHOT.jar

# Specify the command to run your application with OWASP Dependency-Check
CMD ["/bin/sh", "-c", "/usr/bin/wine /app/dependency-check-9.1.0-release/dependency-check/bin/run && java -jar sctp-cohort5-m3-grp3-0.0.1-SNAPSHOT.jar"]
