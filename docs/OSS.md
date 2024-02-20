## OSS

### 使用说明

项目服务器上不存储任何文件，所有文件资源均存储到阿里云 [OSS](https://www.aliyun.com/product/oss) 中。

### 仓库划分

| bucket                | 读写权限 | 名称     | 用途                            | 访问域名                     |
|-----------------------|------|--------|-------------------------------|--------------------------|
| *lifehelper-main*     | 公共读  | 主仓库    | 存储用户活动产生的文件资源，例如头像等           | res.lifehelper.com.cn    |
| *lifehelper-temp*     | 公共读  | 临时仓库   | 用户上传的资源，只允许上传至当前仓库，经处理后转移至主仓库 | temp.lifehelper.com.cn   |
| *lifehelper-official* | 公共读  | 官方资源仓库 | 存储管理员手动上传的资源，例如 `logo.png` 等  | assets.lifehelper.com.cn |
| *lifehelper-web*      | 公共读  | 网站仓库   | 托管PC端网站                       | www.lifehelper.com.cn    |
| *lifehelper-config*   | 私有   | 私密配置仓库 | 存储用于自动化构建的配置文件                | 无                        |
