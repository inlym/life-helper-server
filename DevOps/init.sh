#!/bin/bash

################################## 初始化服务器脚本 ###################################

### 运行说明
# 1. 当前脚本用于服务器初始化，仅在服务器重置后（包含新购机器、重装系统等）执行一次即可。
# 2. 在日常 CI/CD 过程中，无需执行当前脚本。

### 运行环境
# 1. 由“云效”执行自动化脚本，环境变量也由“云效”注入。
# 2. 部署机（即应用服务器）只需要 Docker 环境即可，Docker 镜像托管在阿里云容器镜像服务。

### 文档地址
# 1. 云效-环境变量 -> https://help.aliyun.com/document_detail/153688.html?userCode=lzfqdh6g
# 2. 阿里云-容器镜像服务 -> https://www.aliyun.com/product/acr?userCode=lzfqdh6g

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
