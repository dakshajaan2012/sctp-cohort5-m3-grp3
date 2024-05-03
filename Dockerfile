

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
FROM openjdk:17-oracle AS build
WORKDIR /app

# Copy the Maven wrapper files and project description
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copy the application source code
COPY src ./src

# Build the application
RUN ./mvnw install -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-oracle
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/sctp-cohort5-m3-grp3-0.0.1-SNAPSHOT.jar /app/sctp-cohort5-m3-grp3-0.0.1-SNAPSHOT.jar

# Specify the command to run your application
CMD ["java", "-jar", "sctp-cohort5-m3-grp3-0.0.1-SNAPSHOT.jar"]
