# MySql安装

标签（空格分隔）： 编译环境

---

###  1. [官网下载MySql][1]
下载**.zip格式**绿色版。按电脑系统选择X64或X32。
###  2. 说明
如果你下载的是**msi格式**的，可以直接点击安装，按照它给出的安装提示进行安装，一般MySQL将会安装在C:\Program Files\MySQL\MySQL Server 5.6 该目录中；如果你下载的是**zip格式**，自己解压，解压缩之后其实MySQL就可以使用了，但是要进行配置。下面说下配置的方法。
###  3. 解压
解压之后可以将该文件夹改名，放到合适的位置。个人建议把文件夹改名为MySQL Server 5.6，放到C:\Program Files\MySQL（为例）路径中。当然你也可以放在你想要放的位置。
### 4. 配置环境变量
选择PATH,在其后面添加(注意是追加,不是覆盖): 你的mysql bin文件夹的路径 (如:C:\Program Files\MySQL\MySQL Server 5.6\bin )

> PATH=.......;C:\Program Files\MySQL\MySQL Server 5.6\bin
 
###  5. 修改配置文件
找到“C:\Program Files\MySQL\MySQL Server 5.6\my-default.ini"配置文件，在其中修改配置：
>  [mysqld]   <br>
   # basedir=C:\Program Files\MySQL\MySQL Server 5.6    <br>
   # datadir=C:\Program Files\MySQL\MySQL Server 5.6\data   <br>

### 6. 安装mysql，启动服务
以管理员身份运行cmd，进入“C:\Program Files\MySQL\MySQL Server 5.6\bin”路径下，输入`mysqld -install`，安装成功。安装成功后，继续在cmd中输入`net start mysql`，显示“服务启动成功”则为成功！
### 7. 登录
服务启动成功之后，就可以登录了，输入`mysql -u root -p`（第一次登录没有密码，直接按回车过）,登录成功！
### 8. 修改密码
你可以查阅[官方文档][2]，在cmd中进行以下操作：
 

    shell> mysql -u root
    mysql> UPDATE mysql.user SET Password = PASSWORD('newpwd')
    ->     WHERE User = 'root';
    mysql> FLUSH PRIVILEGES;
出现“Query OK，3 rows affected（0.24 sec）”则为修改成功。


  [1]: http://dev.mysql.com/downloads/mysql/
  [2]: https://dev.mysql.com/doc/refman/5.6/en/default-privileges.html
