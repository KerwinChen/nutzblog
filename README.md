##NutzBlog


基于nutz的简易博客程序   

----
##基本上可以三种部署方式



### 01 java环境下部署方式
先去搞一个VPS ，然后ssh上去

`git clone git@github.com:daodaovps/nutzblog.git`

修改数据库配置文件 src/main/resources/custom/db.properties

启动起来 mvn jetty:run
或者 sudo nohup mvn jetty:run -Djetty.port=80 > /dev/null 2>&1 &
或者打war包后放在你想放的位置 mvn  war:war 

启动后的样子像这样 
http://demo.javablog.net 

### 02 html静态部署方式
按照01的方式启动好之后，登录后台管理
http://demo.javablog.net/adm/login  admin/admin

点击链接  一键生成静态站点

然后去把 {user.home}/site_html 里的所有文件夹及文件上传到你的FTP空间。 后台配置你的FTP，让java代码帮你上传。
像这个样子的静态站点 http://html.javablog.net 
搜索功能没有，会跳转到某个网站。

### 03 git静态部署方式

跟02一样，先生成好静态网站。
然后git push你的git账号。 
像这个一样https://daodaovps.github.io/ 
具体如何创建git静态网站，自己找找哈。





