# Stage 1: Build the JAR file
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the entire project and build it
COPY src ./src
RUN mvn clean package

# Stage 2: Run the JAR file
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the first stage
COPY --from=build /app/target/app-test-jar-1.0-SNAPSHOT-test.jar /app/app-test-jar-1.0-SNAPSHOT-test.jar

# Set the default command to run the JAR file
CMD ["java", "-jar", "app-test-jar-1.0-SNAPSHOT-test.jar"]
