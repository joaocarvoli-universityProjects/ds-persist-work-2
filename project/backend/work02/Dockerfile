FROM gradle:7.6-jdk17 AS builder

WORKDIR /app
COPY . /app

RUN gradle clean build --no-daemon -x test

FROM eclipse-temurin:17-jdk

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
