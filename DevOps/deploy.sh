#!/bin/bash

# 部署脚本 - 在部署机（即应用服务器）上运行

# 之前构建使用的 Git Commit 的标签名，例如 `1.0.0`
echo "${CI_COMMIT_REF_NAME}"

# 需要先拉取镜像再停止容器，否则中断时间会很久
docker pull "registry-vpc.cn-hangzhou.aliyuncs.com/inlym/lifehelper_server:${CI_COMMIT_REF_NAME}"

# 停止运行并删除旧的容器
docker rm -f lifehelper_server

# 运行新的容器
docker run -e ACTIVE_PROFILE=prod -d -p 23030:23030 --name=lifehelper_server "registry-vpc.cn-hangzhou.aliyuncs.com/inlym/lifehelper_server:${CI_COMMIT_REF_NAME}"
