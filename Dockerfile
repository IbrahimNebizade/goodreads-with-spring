FROM openjdk:17.0.2-slim-buster
WORKDIR application
COPY build/libs/goodreads.jar .
ENTRYPOINT ["java", "-jar","goodreads.jar"]