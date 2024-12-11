# pm2

## 说明

使用 **pm2** 来部署 *jar* 包，作为一种可选的项目部署方式，具有以下优点：

1. 比打包成 Docker 镜像再部署的方式显得更轻量一些，适合作为测试环境的部署方式。
2. 比原生使用 `nohup` 方式运行 *jar* 包，相对更容易维护。

## 前置条件

**pm2** 是一个 *Node.js* 库，需要安装 *Node.js* 和 *npm* 。之后再全局安装 **pm2**，命令如下：

```shell
$ npm install -g pm2
```

## 配置

当前目录下的 **pm2.json** 是运行项目的配置文件，关于各个配置项的含义，可参考 [ECOSYSTEM FILE](https://pm2.keymetrics.io/docs/usage/application-declaration/) 上的说明。

## 运行

首次运行时，将当前目录的 **pm2.json** 文件和构建产生的 `life-helper-web.jar` 文件复制到部署服务器的 `/app` （在 **pm2.json** 配置）目录下，然后进入目录运行以下命令：

```shell
$ pm2 start pm2-dev.json
```

资源更新后，重启命令为：

```shell
$ pm2 reload life-helper-server
```

## 更多

关于 **pm2** 的更多使用方式，可查看 [官网文档](https://pm2.keymetrics.io/docs/usage/quick-start/) 。
