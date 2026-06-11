FROM gradle:8.13-jdk21 AS build

WORKDIR /app

COPY settings.gradle build.gradle  ./
COPY gradle ./gradle

COPY . .

RUN gradle :email_sender:clean :email_sender:bootJar --no-daemon

# -------------------------
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/email_sender/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]