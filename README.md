## 系统简介

本人的一次课设，汽车租赁系统，采用的是springmvc+vue前后端分离，springmvc是学校教的，配置文件一个个敲，没有maven，需要用jar包就去maven仓库找，太苦了，vue是临时学的，使用的是`vue-admin-template`模板，刚开始写真的头大，前后端分离，数据传输不会...一个小bug写一天。系统还有些小问题，不过租车的简单基本业务流程能够完成。

### 开发工具及环境

| 工具    | 版本           | 说明             |
| :------ | :------------- | ---------------- |
| IDEA    | 专业版2021.3.3 | jdk1.8           |
| vscode  |                | 后台管理模板     |
| MySQL   | 5.7.26         | phpstudy集成     |
| tomcat  | 8.5.78         |                  |
| navicat | 16             | 数据库管理图形化 |
| Node.js | 16.18.1        |                  |

### 运行项目

1.先建立数据库，用项目中的sql文件`rental_car.sql`直接导入数据库中

![1670922028902](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\1670922028902.png)

2.用IDEA打开项目，添加tomcat，修改url

![1670922301469](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\1670922301469.png)

![1670922329699](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\1670922329699.png)

![1670922340114](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\1670922340114.png)

3.查看数据库连接，`src/applicationContext.xml`

![1670922458458](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\1670922458458.png)

4.用vscode打开 `vue-admin-template`文件夹，这个模板有文档，可自行查阅，打开终端输入如下命令

``` 
npm i //安装需要的包
```

5.同时运行idea和vscode

idea中点击

![1670923349207](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\1670923349207.png)

vscode在终端内输入

```
npm run dev
```

运行成功后输入账号admin密码123456进入

![1670923484752](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\1670923484752.png)

其他账号密码均为6个0