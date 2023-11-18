FROM eclipse-temurin:17-jdk-jammy

# Set the working directory
WORKDIR /app

# Copy Maven wrapper files and POM
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Resolve Maven dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy the application source code
COPY src ./src

# Build the application
RUN ./mvnw clean install -DskipTests

# Expose the port used by the Spring Boot application
EXPOSE 8000

# Run the application
CMD ["./mvnw", "spring-boot:run"]