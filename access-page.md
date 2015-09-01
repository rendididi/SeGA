#页面访问

标签（空格分隔）： 编译环境

---

### 1. 下载数据库文件
首先下载我们的[项目文件][1]，放在想要的位置，比如“D:\sega”。
我们需要导入的文件为：

    D:\sega\fudanglp-SeGA-fd14a10\ProcessDesigner\src\main\resources\sql\Dump20150831-uar.sql

### 2. 命令行进入MySql
在命令行窗口（cmd）中，输入`mysql -h localhost -u root -p`，回车等待输入密码。进入命令行后可以直接操作mysql命令了。
### 3. 创建数据库uar
进入到`mysql>`后，输入`create database uar;`，创建名称为uar的数据库。
你可以使用`show databases;`来看数据库是否创建成功。
### 4. 导入数据库文件
继续在命令行中输入`exit`或者`quit`退出mysql。显示`c:\users\administrator>`后，输入命令行

    mysql -h localhost -u root -p uar < D:\sega\fudanglp-SeGA-fd14a10\ProcessDesigner\src\main\resources\sql\Dump20150831-uar.sql
按回车等待输入密码就可以了。
###  5. 检验是否已经导入数据库
命令行进入mysql，方法同上。简单介绍下mysql命令：

> mysql->CREATE DATABASE dbname;//创建数据库  <br>
mysql->CREATE TABLE tablename;//创建表   <br>
mysql->SHOW DATABASES;//显示数据库信息，有那些可用的数据库。   <br>
mysql->USE dbname;//选择数据库   <br>
mysql->SHOW TABLES;//显示表信息，有那些可用的表   <br>
mysql->DESCRIBE tablename;//显示创建的表的信息   <br>
  
使用 `use uar;`来进入uar数据库，`show tables;`来查看是否已经导入我们的表。
### 6. 启动jetty服务，访问页面
数据库导入后，命令行进入`D:\sega\fudanglp-SeGA-fd14a10\ProcessDesigner`路径下，输入`mvn clean install`。成功后修改

    `D:\sega\fudanglp-SeGA-fd14a10\ProcessDesigner\src\main\resources\hibernate.cfg.xml`

配置文件，在其中修改下列语句，填写上你的数据库密码：

    <property name="hibernate.connection.password">此处写你的数据库密码</property>
    
保存后，命令行中输入`mvn jetty:run`，成功后，浏览器访问`localhost:8080`。

  [1]: http://fudanglp.github.io/SeGA/
