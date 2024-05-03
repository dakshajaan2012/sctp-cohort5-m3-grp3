FROM eclipse-temurin:17-jdk-jammy
#FROM openjdk:17-oracle AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src
RUN chmod +x mvnw
RUN ./mvnw install -DskipTests

#COPY target/sctp-cohort5-m3-grp3-0.0.1-SNAPSHOT.jar /app/sctp-cohort5-m3-grp3-0.0.1-SNAPSHOT.jar
#CMD ["./mvnw", "spring-boot:run","java -jar sctp-cohort5-m3-grp3-0.0.1-SNAPSHOT.jar"]
CMD ["java", "-jar", "sctp-cohort5-m3-grp3-0.0.1-SNAPSHOT.jar"]