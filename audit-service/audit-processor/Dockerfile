FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=target/audit-processor-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
