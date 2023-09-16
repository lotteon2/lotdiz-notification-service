FROM openjdk:11-slim-buster

WORKDIR /app

ARG ORIGINAL_JAR_FILE=./build/libs/notification-service-1.0.0.jar

COPY ${ORIGINAL_JAR_FILE} notification-service.jar

CMD ["java", "-jar", "/app/notification-service.jar"]
