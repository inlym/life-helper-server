#!/bin/bash

################################### 项目部署脚本 ###################################

### 运行说明
# 1. 需要在各个应用服务器（即部署机）上都运行一遍。

### 运行环境
# 1. 由“云效”执行自动化脚本，在部署机上运行。

### 文档地址
# 1. 云效-环境变量 -> https://help.aliyun.com/document_detail/153688.html?userCode=lzfqdh6g
# 2. 阿里云-容器镜像服务 -> https://www.aliyun.com/product/acr?userCode=lzfqdh6g

# 之前构建使用的 Git Commit 的标签名，例如 `1.0.0`
echo "${CI_COMMIT_REF_NAME}"

# 需要先拉取镜像再停止容器，否则中断时间会很久
docker pull "registry-vpc.cn-hangzhou.aliyuncs.com/inlym/lifehelper_server:${CI_COMMIT_REF_NAME}"

# 停止运行并删除旧的容器
docker rm -f lifehelper_server

# 运行新的容器
docker run -e ACTIVE_PROFILE=prod -d -p 23030:23030 --name=lifehelper_server "registry-vpc.cn-hangzhou.aliyuncs.com/inlym/lifehelper_server:${CI_COMMIT_REF_NAME}"
