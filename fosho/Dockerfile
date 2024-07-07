FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
COPY target/fosho-0.0.1-SNAPSHOT.jar /app/fosho.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "fosho.jar"]