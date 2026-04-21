# Build stage
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline -q

COPY src src
RUN ./mvnw package -DskipTests -q

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/app-0.0.1-SNAPSHOT.jar app.jar

# OpenShift runs containers with an arbitrary UID in the root group (0).
# Granting group write on /app ensures the process can create temp files.
RUN chown -R 1001:0 /app && chmod -R g+rwX /app

USER 1001

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
