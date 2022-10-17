FROM amazoncorretto:17-alpine
EXPOSE 8080
ADD target/*.jar backlog.jar
ENTRYPOINT [ "java", "-jar", "backlog.jar" ]