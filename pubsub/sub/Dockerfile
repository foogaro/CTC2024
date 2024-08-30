FROM openjdk:21-slim
LABEL authors="foogaro"

#WORKDIR /app/lib
#COPY ./target/dependency/* .
WORKDIR /app
COPY ./target/*.jar ./app.jar

EXPOSE 8080
#ENTRYPOINT ["java","-server", "-Xms5g", "-Xmx5g", "-Xss1024k", "-XX:MaxMetaspaceSize=1g","-cp", "/app/lib/*", "-jar","/app/look-alike-1.0.0.jar"]ENTRYPOINT ["java","-server", "-Xms5g", "-Xmx5g", "-Xss1024k", "-XX:MaxMetaspaceSize=1g","-cp", "/app/lib/*", "-jar","/app/look-alike-1.0.0.jar"]
ENTRYPOINT ["java","-server", "-Xms5g", "-Xmx5g", "-Xss1024k", "-XX:MaxMetaspaceSize=1g","-jar"]

CMD ["/app/app.jar"]
