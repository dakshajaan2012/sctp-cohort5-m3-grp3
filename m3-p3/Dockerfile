

# Use a base image with Java installed
FROM adoptopenjdk/openjdk11:alpine-jre

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/m3-p3-0.0.1-SNAPSHOT.jar /app

# Set an environment variable for localhost
ENV PORT=3000

# Specify the command to run your application
CMD ["java", "-jar", "m3-p3-0.0.1-SNAPSHOT.jar"]