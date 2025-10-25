FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:21-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

RUN addgroup --system --gid 1000 appuser && \
    adduser --system --uid 1000 --gid 1000 appuser
USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
