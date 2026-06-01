FROM gradle:8.13-jdk21 AS build

WORKDIR /app

COPY . .

RUN gradle :backend:bootJar --no-daemon

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/backend/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]