

FROM openjdk:17-oracle

WORKDIR /app

COPY target/sctp-cohort5-m3-grp3-0.0.1-SNAPSHOT.jar /app

CMD ["java", "-jar", "sctp-cohort5-m3-grp3-0.0.1-SNAPSHOT.jar"]