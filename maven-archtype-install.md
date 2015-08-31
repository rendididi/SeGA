# Maven安装 

标签（空格分隔）： 编译环境

---

1. [Maven下载地址][1]   
2. 下载后，解压。新建环境变量**MAVEN_HOME**，设置值为maven中bin文件存放的路径。例如：MAVEN_HOME：E:\Program Files\maven\apache-maven-3.3.3   <br> 
3. 在**PATH**里加入maven的bin的路径。例如：%MAVEN_HOME%\bin;   <br> 
4. 由于Maven依赖Java运行环境，因此使用Maven之前需要配置Java的运行环境。下载并安装JDK，配置JDK的环境变量**JAVA_HOME**，否则maven将无法使用。在windows命令提示符下，可输入`java -version`，`javac -version`验证。   <br> 
5. 配置完毕后，在Windows命令提示符下，输入`mvn -v`测试是否安装成功。   <br> 
6. 进入[Struts 2 Maven Archetypes页面][2]，找到以下代码，将其输入到windows 命令行中：

     mvn archetype:generate -B -DgroupId=com.mycompany.mysystem \
                            -DartifactId=myWebApp \
                            -DarchetypeGroupId=org.apache.struts \
                        -DarchetypeArtifactId=struts2-archetype-angularjs \
                            -DarchetypeVersion=<CURRENT_STRUTS_VERSION> \
                            -DremoteRepositories=http://struts.apache.org

其中，要把`<CURRENT_STRUTS_VERSION>`改为2.5。整行命令要在一行中，也就是说您需要把‘\\’符号删掉，将命令整理在一行中。
      
**即** 在windows命令提示符下，输入以下命令：
`mvn archetype:generate -B -DgropId=com.mycompany.mysystem -DartifactId=myWebApp -DarchetypeGroupId=org.apache.struts -DarchetypeArtifactId=struts2-archetype-angularjs -DarchetypeVersion=2.5 -DremoteRepositories=http://struts.apache.org`
然后回车自行安装即可。

 


 


  [1]: http://maven.apache.org/download.cgi?Preferred=http://mirrors.hust.edu.cn/apache/
  [2]: https://struts.apache.org/docs/struts-2-maven-archetypes.html
