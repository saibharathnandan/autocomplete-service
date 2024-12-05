# Use official OpenJDK as a base image
FROM openjdk:17-jdk-slim
LABEL authors="saibharath"

# Copy the compiled jar file into the container
COPY target/autocomplete-service-0.0.1-SNAPSHOT.jar /app.jar

# Expose the port the app runs on
EXPOSE 9090

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
