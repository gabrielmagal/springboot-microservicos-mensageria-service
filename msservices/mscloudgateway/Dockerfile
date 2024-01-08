FROM openjdk:17
WORKDIR /app
COPY /target/*.jar ./app.jar
ARG ENV_EUREKA_SERVER=localhost
ARG ENV_KEYCLOAK_SERVER=localhost
ARG ENV_KEYCLOAK_PORT=8081
ENTRYPOINT java -jar app.jar