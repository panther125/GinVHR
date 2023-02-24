## 项目介绍

低配版微人事 本项目在大佬 [江南一点雨](https://github.com/lenve/vhr) 的微人事项目的二次开发，项目亮点（拉低了大佬的项目变成了一个springboot+mybatis-plus的CRUD项目）


- [项目部署视频教程（旧版）](https://mp.weixin.qq.com/s/qN01Le434FWom0c3jqiQmA)
- [项目部署视频教程（新版）](https://mp.weixin.qq.com/s/dcJv6BIVFPnokI8nBf4IrQ)
- [项目其他相关资料](./README_zh.md)

### 项目技术栈

#### 后端技术栈

1. Spring Boot
3. MyBatis-plus
4. MySQL
5. Redis
8. WebSocket

#### 前端技术栈

1. Vue
2. ElementUI
3. axios
4. vue-router
5. Vuex
6. WebSocket
7. vue-cli4

### 项目效果图

首先，不同的用户在登录成功之后，根据不同的角色，会看到不同的系统菜单，完整菜单如下：

![p278](https://raw.githubusercontent.com/wiki/lenve/vhr/doc/p278.png)

不同用户登录上来之后，可能看到的会有差异，如下：

![p279](https://raw.githubusercontent.com/wiki/lenve/vhr/doc/p279.png)

每个用户的角色是由系统管理员进行分配的，系统管理员给用户分配角色的页面如下：

![p280](https://raw.githubusercontent.com/wiki/lenve/vhr/doc/p280.png)

系统管理员也可以管理不同角色可以操作的资源，页面如下：

![p281](https://raw.githubusercontent.com/wiki/lenve/vhr/doc/p281.png)

## 快速部署

1. clone 项目到本地 `git@github.com:lenve/vhr.git`
2. 导入数据库
3. 下载依赖
4. 启动Redis
5. 运行项目

浏览器输入localhost:9001即可浏览