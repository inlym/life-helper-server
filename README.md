<div align="center">
  <br>
  <img alt="小鸣助手 Logo" src="https://static.lifehelper.com.cn/static/project/logo.png" style="height:120px;" />
  <br>
  <h1>小鸣助手</h1>
  <h5>让生活更简单一些</h5>
</div>

## 🤓 项目介绍

「小鸣助手」是一个生活服务类小程序，主要为用户的日常生活提供一些便捷工具，例如天气查询、时间规划、生活记录等。目前该小程序已稳定运行近4年，为近10万用户提供了生活帮助。

读者可直接扫描以下小程序码进行体验：

![image](https://static.lifehelper.com.cn/static/project/qrcode.jpg)

## 💡 项目特点

1. 线上正式运行的项目，不是 demo，历经4年，久经用户考验。
2. 开发尽量遵照业界最佳实践，可作为学习样板。
3. 跟随版本更新，包含 Java、Spring Boot 等，尽量使用**最新稳定版**，保持技术栈不落后。

## 🍱 源码仓库

笔者在开发项目时，遵照了业界最佳实践，读者可通过研读项目源码来学习相关技术栈。按照功能分类，将项目拆分为了4个代码仓库，分别为：

| 仓库                                                                          | 定位    | 技术栈                                                                                  |
|-----------------------------------------------------------------------------|-------|--------------------------------------------------------------------------------------|
| [life-helper-server](https://github.com/inlym/life-helper-server)           | 服务端   | `Spring Boot` + `Spring Security` + `JWT` + `MyBatis` + `MySQL` + `Redis` + `Docker` |
| [life-helper-backend](https://github.com/inlym/life-helper-backend)         | 服务端   | `Node.js` + `Nest.js` + `TypeScript` + `Typeorm` + `MySQL` + `Redis` + `Docker`      |
| [life-helper-miniprogram](https://github.com/inlym/life-helper-miniprogram) | 小程序端  | `TypeScript` + `Scss`                                                                |
| [life-helper-web](https://github.com/inlym/life-helper-web)                 | Web 端 | `Angular` + `TypeScript` + `Scss` + `RxJS` +  `Webpack`                              |

## 🗂️ 目录结构

当前项目（[life-helper-server](https://github.com/inlym/life-helper-server)）是一个标准的 Spring Boot 项目，几乎遵照了所有的 Spring Boot
最佳实践（至少笔者认为自己执行了最严格的标准）。

关于项目的目录结构，一般有2种常见的思路：

1.

方案一：以定位划分。这种方案的核心点在于以“代码”的角度，将同功能的代码文件放在一起，例如在电商项目中，商品、订单模块都有控制器，那么所有的控制器文件都放在 `controllers`
目录下，所有的服务类都放在 `services` 目录下。

2.

方案二：以功能划分。这种方案的核心点在于以“功能”的角度，将同业务模块的代码文件放在一起，例如不管是控制器还是服务类，只要是商品模块的代码文件，都放到 `goods`
目录下。

笔者在项目实践中，采用的是“**方案二**
”，笔者认为在大型项目中，方案二更容易维护。以下是当前项目（[life-helper-server](https://github.com/inlym/life-helper-server)）的目录结构（_todo_）。

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

## 🚀 技术栈

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

## ❓ 常见问题

| 序号  | 问题                                                     |
|-----|--------------------------------------------------------|
| 1   | [如何启动项目？](https://github.com/inlym/life-helper-server) |

## 📞 交流沟通

如果你在使用「小鸣助手」小程序过程中，遇到任何问题，或者有任何意见建议，你可以通过以下方式联系我：

- [x] 邮箱：_inlym@qq.com_
- [x] 公众号：搜索公众号「**1鸣的写字台**」或微信号 `iam1ming`。

## 📄 许可证

本项目使用 [MIT](LICENSE) 许可证。
