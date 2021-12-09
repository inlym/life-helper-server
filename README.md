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
│   │       ├── templates/                # 用于存放模板文件
│   │       └── application.yml           # 用于配置项目运行所需的配置数据
│   └── test/                             # 单元测试程序目录
├── .editorconfig                         # `EditorConfig` 插件的配置文件，用于控制一致的代码风格
├── .gitignore                            # `Git` 的配置文件，用户控制不被 `Git` 跟踪的文件和目录
├── Dockerfile                            # Docker 构建文件
└── README.md                             # 项目介绍文档，用于对外展现项目基本介绍

```


## 相关仓库

### 服务端

仓库（当前仓库）地址： [life-helper-server](https://github.com/inlym/life-helper-server)

技术栈： `Spring Boot` + `MySQL` + `Redis` + `Docker`


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
