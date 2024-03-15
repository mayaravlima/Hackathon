FROM amazoncorretto:17.0.7-alpine

COPY build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]