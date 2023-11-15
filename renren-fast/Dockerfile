FROM openjdk:8-jre-slim
EXPOSE 8080
WORKDIR /home
COPY target/*.jar /home
ENTRYPOINT java -jar *.jar