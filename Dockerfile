FROM maven:3.8-eclipse-temurin-8

ARG BACKLOG_DB_URL
ARG DB_SB_PW
ARG DB_SB_USR
ARG JWT_SECRET

ENV BACKLOG_DB_URL=${BACKLOG_DB_URL}
ENV DB_SB_PW=${DB_SB_PW}
ENV DB_SB_USR=${DB_SB_USR}
ENV JWT_SECRET=${JWT_SECRET}

RUN printenv

EXPOSE 8080

COPY ./ ./
RUN mvn -f ./pom.xml clean package

ADD target/*.jar /backlog.jar

ENTRYPOINT [ "java", "-jar", "backlog.jar" ]