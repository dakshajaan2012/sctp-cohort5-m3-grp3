

# Use a base image with Java installed_updated
FROM openjdk:17-oracle

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/sctp-cohort5-m3-grp3-0.0.1-SNAPSHOT.jar /app


# Specify the command to run your application
CMD ["java", "-jar", "sctp-cohort5-m3-grp3-0.0.1-SNAPSHOT.jar"]