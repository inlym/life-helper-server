#!/bin/bash

#################### 服务器初始化脚本 ####################

### 说明
# 当前脚本用于服务器初始化，仅在服务器重置后（包含新购机器、重装系统等）执行一次，在日常 CI/CD 过程中，无需执行当前脚本。

### 备注
# 目前项目服务器只需要 Docker 环境即可。

# 在阿里云容器镜像服务中使用的用户名
echo "${DOCKER_USERNAME}"

# 在阿里云容器镜像服务中使用的密码
echo "${DOCKER_PASSWORD}"

# 更新软件清单
apt update

# 安装 Docker
apt install -y docker.io

# 登录 Docker（备注：这里用了阿里云容器镜像服务）
echo "${DOCKER_PASSWORD}" | docker login -u "${DOCKER_USERNAME}" --password-stdin registry-vpc.cn-hangzhou.aliyuncs.com
