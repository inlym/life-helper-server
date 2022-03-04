# life-helper-server

「我的个人助手」小程序服务端代码

## 目录结构

```
life-helper-server
├── src/                                  # 项目核心代码
│   ├── main/
│   │   ├── java/                         # 入口类及程序的开发目录
│   │   └── resources/                    # 资源文件目录，主要用于存放静态文件和配置文件
│   │       ├── static/                   # 用于存放静态资源，如 CSS 文件、Javascript 文件、图片等
│   │       ├── templates/                # 用于存放模板文件，如 Thymeleaf 模板文件等
│   │       └── application.yml           # 用于配置项目运行所需的配置数据
│   └── test/                             # 单元测试程序目录
├── .editorconfig                         # `EditorConfig` 插件的配置文件，用于控制一致的代码风格
├── .gitignore                            # `Git` 的配置文件，用户控制不被 `Git` 跟踪的文件和目录
├── Dockerfile                            # Docker 构建文件
├── pom.xml                               # 用于配置项目基本信息和项目依赖
└── README.md                             # 项目介绍文档，用于对外展现项目基本介绍

```

## 技术栈

|       技术栈       | 链接                                          |
|:---------------:|---------------------------------------------|
|   Spring Boot   | https://spring.io/projects/spring-boot      |
| Spring Security | https://spring.io/projects/spring-security  |
|     Lombok      | https://www.projectlombok.org/              |
|      Maven      | https://maven.apache.org/                   |
|     MyBatis     | https://mybatis.org/mybatis-3/zh/index.html |
|     Docker      | https://www.docker.com/                     |
|      MySQL      | https://www.mysql.com/cn/                   |
|      Redis      | https://redis.io/                           |
|      Druid      | https://druid.apache.org/                   |
|       JWT       | https://jwt.io/                             |
|     Swagger     | https://swagger.io/                         |

## 运行

按照以下流程可以将 [本项目](https://github.com/inlym/life-helper-server) 启动：

1. 将代码克隆至本地

```sh
git clone https://github.com/inlym/life-helper-server.git
````

2. 准备相关资源并完成配置

本项目依赖 MySQL 和 Redis，请自行准备相关资源并在 `src/main/resources` 目录下创建 `application-dev.yml` 配置文件，
配置文件内容请直接复制 `application-demo.yml` ，然后将对应资源替换为实际地址。

3. 启动项目

完成配置并安装依赖后，启动 `com.inlym.lifehelper.Application` 的 `main` 方法启动项目。

4. 访问项目

http://localhost:23010

## 相关仓库

### 服务端

仓库（当前仓库）地址： [life-helper-server](https://github.com/inlym/life-helper-server)

技术栈： `Spring Boot` + `Spring Security` + `MyBatis` + `MySQL` + `Redis` + `Docker`

### 服务端（旧，使用 Nest.js 框架，已弃用）

仓库地址： [life-helper-backend](https://github.com/inlym/life-helper-backend)

技术栈： `Node.js` + `Nest.js` + `TypeScript` + `Typeorm` + `MySQL` + `Redis` + `Docker`

### 小程序端

仓库地址： [life-helper-miniprogram](https://github.com/inlym/life-helper-miniprogram)

技术栈： `原生小程序` + `自定义的一套框架加强工具`

![](https://img.inlym.com/ed5676d20f6243328c2e89a1403e4ff0.jpg)

### Web 端

仓库地址： [life-helper-frontend](https://github.com/inlym/life-helper-frontend)

技术栈： `Angular` + `TypeScript` + `Sass` + `RxJS` + `Swagger` + `Webpack`

Web 地址： [我的个人助手](https://www.lifehelper.com.cn/)
