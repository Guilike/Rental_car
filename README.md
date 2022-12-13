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

![image](https://user-images.githubusercontent.com/65472477/207279787-34605e42-98b5-47a0-9715-982d63821fde.png)

2.用IDEA打开项目，添加tomcat，修改url

![image](https://user-images.githubusercontent.com/65472477/207279870-bdf847dd-1f44-47dc-b369-12bd6d613cc2.png)


![image](https://user-images.githubusercontent.com/65472477/207279912-a0b70ed5-97fd-4a95-8f5c-3e6e881433e6.png)


![image](https://user-images.githubusercontent.com/65472477/207279956-50f6881f-5511-44ab-bd90-ed266b1316cc.png)


3.查看数据库连接，`src/applicationContext.xml`

![image](https://user-images.githubusercontent.com/65472477/207279992-cfe818ba-bab0-4003-b296-34b91b52551d.png)

4.用vscode打开 `vue-admin-template`文件夹，这个模板有文档，可自行查阅，打开终端输入如下命令

``` 
npm i //安装需要的包
```

5.同时运行idea和vscode

idea中点击

![image](https://user-images.githubusercontent.com/65472477/207280042-64bfe2c1-3a61-43cc-81d1-c119e8d19e4d.png)

vscode在终端内输入

```
npm run dev
```

运行成功后输入账号admin密码123456进入

![image](https://user-images.githubusercontent.com/65472477/207280092-afe2d8e4-d6d1-4cc1-bd40-11b825777397.png)

其他账号密码均为6个0
