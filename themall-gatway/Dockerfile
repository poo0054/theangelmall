FROM adoptopenjdk/openjdk8:jre8u392-b08
EXPOSE 80
WORKDIR /home
COPY target/*.jar /home
ENTRYPOINT java -jar *.jar --spring.profiles.active=prod