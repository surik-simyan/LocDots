FROM openjdk:23
EXPOSE 8080:8080
RUN mkdir /app
COPY ./build/libs/*-all.jar /app/locdots-server.jar
ENTRYPOINT ["java","-jar","/app/locdots-server.jar"]