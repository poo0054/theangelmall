FROM openjdk:8-jre-slim
EXPOSE 8080
WORKDIR /home
COPY target/*.jar /home
# -Dspring.profiles.active=prod
ENTRYPOINT java -jar   *.jar
