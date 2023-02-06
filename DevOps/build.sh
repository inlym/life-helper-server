#!/bin/bash

################################### 项目构建脚本 ###################################

### 运行说明
# 1. 以 git 标签（即版本号）名发起构建。

### 运行环境
# 1. 由“云效”执行自动化脚本，在构建机上运行。

### 构建目标
# 输出 Docker 镜像，并上传至阿里云镜像服务。

### 文档地址
# 1. 云效-环境变量 -> https://help.aliyun.com/document_detail/153688.html?userCode=lzfqdh6g
# 2. 阿里云-容器镜像服务 -> https://www.aliyun.com/product/acr?userCode=lzfqdh6g

# 要构建的 Git Commit 的标签名，例如 `1.0.0`
echo "${CI_COMMIT_REF_NAME}"

# 将代码克隆至本地
git clone https://github.com/inlym/life-helper-server.git  --depth=1  --branch "${CI_COMMIT_REF_NAME}" /root/workspace/life-helper-server

# 进入工作目录
cd /root/workspace/life-helper-server || exit

# 从 OSS 中下载配置文件（为保密起见，在 github 上的源码不包含配置文件）
ossutil cp -r oss://lifehelper-config/config/application-prod.yml /root/workspace/life-helper-server/src/main/resources/ --update

# 进行 Java 构建
mvn -B clean package -Dmaven.test.skip=true

# 进行 Docker 镜像构建
docker build -t "registry-vpc.cn-hangzhou.aliyuncs.com/inlym/lifehelper_server:${CI_COMMIT_REF_NAME}" .

# 将 Docker 镜像推送至阿里云镜像仓库
docker push "registry-vpc.cn-hangzhou.aliyuncs.com/inlym/lifehelper_server:${CI_COMMIT_REF_NAME}"
