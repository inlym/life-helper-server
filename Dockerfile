FROM openjdk:17-alpine
LABEL maintainer="inlym@qq.com"
ENV TZ="Asia/Shanghai"
ADD target/lifehelper.jar /app.jar
EXPOSE 23010
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=prod"]
