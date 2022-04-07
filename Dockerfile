FROM openjdk:17-alpine
LABEL maintainer="inlym@qq.com"
ENV TZ="Asia/Shanghai"
ADD target/${JAR_FILE} /app.jar
EXPOSE 23030
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=prod"]
