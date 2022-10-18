FROM arm64v8/maven:3.8.6-eclipse-temurin-17-alpine

ARG BACKLOG_DB_URL
ARG DB_PASS
ARG DB_USER
ARG JWT_SECRET

ENV BACKLOG_DB_URL=${BACKLOG_DB_URL}
ENV DB_PASS=${DB_PASS}
ENV DB_USER=${DB_USER}
ENV JWT_SECRET=${JWT_SECRET}

COPY ./ ./

RUN mvn clean install

COPY target/backlog.jar backlog.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "backlog.jar"]