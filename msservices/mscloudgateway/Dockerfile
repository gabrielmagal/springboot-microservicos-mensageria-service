FROM maven:3.8.5-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app
COPY --from=build ./app/target/*.jar ./app.jar
ARG ENV_EUREKA_SERVER=eureka-container
ARG ENV_KEYCLOAK_SERVER=localhost
ARG ENV_KEYCLOAK_PORT=8081
ENTRYPOINT java -jar app.jar