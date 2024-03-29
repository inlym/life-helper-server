#!/bin/bash

################################### 项目部署脚本 ###################################

### 运行说明
# 1. 需要在各个应用服务器（即部署机）上都运行一遍。

### 运行环境
# 1. 由“云效”执行自动化脚本，在部署机上运行。
# 2. 部署机即应用服务器，处于 VPC 内。

### 文档地址
# 1. 云效-环境变量 -> https://help.aliyun.com/document_detail/153688.html?userCode=lzfqdh6g
# 2. 阿里云-容器镜像服务 -> https://www.aliyun.com/product/acr?userCode=lzfqdh6g

# 镜像仓库地址
echo "${DOCKER_REPOSITORY_VPC}"

# 构建使用的 Git Commit 的标签名，例如 `1.0.0`
echo "${CI_COMMIT_REF_NAME}"

# 镜像名称
IMAGE_NAME="${DOCKER_REPOSITORY_VPC}:${CI_COMMIT_REF_NAME}"

# 需要先拉取镜像再停止容器，否则中断时间会很久
docker pull "${IMAGE_NAME}"

# 停止运行并删除旧的容器
docker rm -f lifehelper_server

# 运行新的容器
# 注意：因为网络模式指定了 `host`，因此 `-p` 参数是无效的，所以去掉了。
docker run -d --name=lifehelper_server --network=host --restart=always "${IMAGE_NAME}"

# 删除已弃用的镜像
docker image prune -af
