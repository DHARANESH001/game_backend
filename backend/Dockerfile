# Build stage
FROM maven:3.9.5-openjdk-17-slim as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Application stage
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Wait for MySQL to be ready and start the application
CMD bash -c 'echo "Waiting for MySQL to be ready..." && \
    while ! mysqladmin ping -h "caboose.proxy.rlwy.net" -P 17233 -u root -p"EwgSAjByNoVqDnIqlfzbejPtyWvrEgpu" --silent; do \
        sleep 1; \
    done && \
    echo "MySQL is ready" && \
    echo "Starting Spring Boot application..." && \
    java -jar app.jar'
