# Use the official OpenJDK base image
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/vegetable-vendor-0.0.1-SNAPSHOT.jar /app

# Expose the port that your Spring Boot application will run on
EXPOSE 8080

# Specify the command to run on container startup
CMD ["java", "-jar", "vegetable-vendor-0.0.1-SNAPSHOT.jar"]
