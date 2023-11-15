FROM java:8u92-jre-alpine
EXPOSE 8080
WORKDIR /home
COPY target/*.jar /home
ENTRYPOINT java -jar *.jar