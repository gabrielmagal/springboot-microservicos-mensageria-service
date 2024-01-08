FROM openjdk:17
WORKDIR /app
COPY /target/*.jar ./app.jar
ARG ENV_RABBITMQ_SERVER=localhost
ARG ENV_EUREKA_SERVER=localhost
ENTRYPOINT java -jar app.jar