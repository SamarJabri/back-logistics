# First stage: Build the application using Maven and OpenJDK 17
FROM maven:3.8.4-openjdk-17 AS builder

# Set the working directory for the build
WORKDIR /build

# Copy the pom.xml file to download Maven dependencies
COPY pom.xml .

# Download Maven dependencies
RUN mvn dependency:go-offline

# Copy the source code to the build directory
COPY src ./src

# Package the application
RUN mvn package -DskipTests

# Second stage: Use a lightweight JDK image for the runtime environment
FROM openjdk:17-jdk-alpine

# Set the working directory for the application
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /build/target/logistics-1.0.0.jar /app/logistics.jar

# Expose the application port (if necessary)
# EXPOSE 8080

# Default command to run the JAR
CMD ["java", "-jar", "/app/logistics.jar"]
