FROM openjdk:17 AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
#RUN #mvn clean package

#
# Package stage
#
FROM eclipse-temurin:17-jre-jammy
ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=build $JAR_FILE /app/agent.jar
EXPOSE 8080
ENTRYPOINT java -jar /app/agent.jar