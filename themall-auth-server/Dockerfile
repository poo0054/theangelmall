FROM adoptopenjdk/openjdk8:jre8u392-b08
EXPOSE 8080
WORKDIR /home
COPY target/*.jar /home

ENV ACTIVE="prod"
CMD ["sh","-c","java -jar  *.jar --spring.profiles.active=${ACTIVE} "]