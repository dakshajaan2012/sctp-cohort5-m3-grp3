FROM eclipse-temurin:17-jdk-jammy
#FROM openjdk:17-oracle AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src
RUN chmod +x mvnw
RUN ./mvnw install -DskipTests
CMD ["./mvnw", "spring-boot:run"]