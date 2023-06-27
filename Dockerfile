FROM openjdk:17-alpine

CMD ["./mvnw", "clean", "package"]
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} hseCopyFile-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "hseCopyFile-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080