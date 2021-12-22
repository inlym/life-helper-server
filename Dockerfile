FROM openjdk:17-alpine
LABEL maintainer="inlym@qq.com"
ENV TZ="Asia/Shanghai"
ARG JAR_FILE
ADD target/lifehelper.jar /app.jar
EXPOSE 3030
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=prod"]
