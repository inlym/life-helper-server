# DevOps 主要流程
#
# 构建 Java，输出 `.jar` 文件（跳过测试）
# `mvn clean package -Dmaven.test.skip`
#
# 构建 Docker，输出 Docker 镜像文件
# `docker build -t ${DOCKER_TAG} .`
#
# 推送 Docker 镜像至镜像仓库
# `docker push ${REGISTRY_ADDRESS}:${DOCKER_TAG}`
#
# 运行 Docker 镜像
# `docker run -d -p 23030:23030 --name=lifehelper_server  ${REGISTRY_ADDRESS}:${DOCKER_TAG}`

## 注意事项（1）
# 当前 `Dockerfile` 仅用于通用构建模式，在 `pom.xml` 注册的 `jib-maven-plugin` 插件构建 Docker 未使用当前 `Dockerfile`。

# =========================================================================== #


# 镜像地址：https://hub.docker.com/_/openjdk/
FROM openjdk:17-alpine

LABEL maintainer="inlym@qq.com"

# `.jar` 包路径
ARG JAR_FILE="target/lifehelper.jar"

# 开放端口
ARG EXPOSE_PORT=23030

# 启用的配置文件
ARG ACTIVE_PROFILE=prod

# 设定时区
ENV TZ="Asia/Shanghai"

COPY ${JAR_FILE} /app.jar

RUN echo 'Asia/Shanghai' >/etc/timezone

EXPOSE ${EXPOSE_PORT}

ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=${ACTIVE_PROFILE}"]
