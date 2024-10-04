# Use an official Maven image to build the application
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Build the JAR for both profiles
RUN mvn clean package -PbasicAlgorithm,advancedAlgorithm

# Use a smaller JDK base image for the runtime
FROM openjdk:17-jdk-alpine

# Set the working directory for the runtime container
WORKDIR /app

# Copy both JARs from the build stage to the runtime container
COPY --from=build /app/target/HappyQA-1.0-basicAlgorithm.jar ./basicAlgorithm.jar
COPY --from=build /app/target/HappyQA-1.0-advancedAlgorithm.jar ./advancedAlgorithm.jar

# Copy the text file if it's needed for your application
COPY releases.txt ./releases.txt

# Set default values for parameters
ENV ALGORITHM_TYPE=advanced
ENV SPRINT_DURATION=10

# Copy the script to the container
COPY entrypoint.sh /app/entrypoint.sh

# Make the script executable
RUN chmod +x /app/entrypoint.sh

# Set the script as the entry point
CMD ["/app/entrypoint.sh"]
