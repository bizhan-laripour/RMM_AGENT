FROM maven:latest AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN mvn  clean package install

FROM openjdk:17-oracle
ARG JAR_FILE=/usr/app/target/*.jar
ENV SPRING_RABBITMQ_HOST=0.0.0.0
ENV SPRING_RABBITMQ_PORT=0000
ENV SPRING_DATA_MONGODB_HOST=0.0.0.1
ENV SPRING_DATA_MONGODB_PORT=0001
COPY --from=build $JAR_FILE /app/agent.jar
EXPOSE 8082
ENTRYPOINT java -jar /app/agent.jar