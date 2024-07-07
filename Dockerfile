FROM openjdk:22
EXPOSE 8080:8080
RUN mkdir /app
COPY ./server/build/libs/*-all.jar /app/locdots-server.jar
ENTRYPOINT ["java","-jar","/app/locdots-server.jar"]