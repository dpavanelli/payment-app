FROM eclipse-temurin:17.0.2_8-jre-alpine

WORKDIR /app

COPY build/libs/backend-0.0.1-SNAPSHOT.jar application.jar

EXPOSE 8080

CMD ["java", "-jar", "application.jar"]
