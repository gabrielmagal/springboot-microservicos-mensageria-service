FROM openjdk:17
WORKDIR /app
COPY /target/*.jar ./app.jar
ARG ENV_RABBITMQ_SERVER=rabbitmq
ARG ENV_EUREKA_SERVER=eureka-container
ENTRYPOINT java -jar app.jar