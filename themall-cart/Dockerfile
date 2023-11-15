FROM openjdk:8-jre-slim
EXPOSE 8080

VOLUME /tmp
ADD target/*.jar  /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","-Xmx300m","/app.jar","--spring.profiles.active=prod"]