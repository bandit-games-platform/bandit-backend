FROM --platform=linux/amd64 gradle:8.5.0-jdk21-alpine AS build
WORKDIR /app
COPY . .
RUN gradle :application:bootJar

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/application/config ./config
COPY --from=build /app/application/build/libs/application.jar ./bandit-backend.jar
EXPOSE 8090
CMD ["java", "-jar", "bandit-backend.jar"]
