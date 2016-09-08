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
http://w.javablog.net

### 02 html静态部署方式
按照01的方式在自己的电脑上部署.
启动好之后，登录后台管理
像这样 http://127.0.0.1:8080/adm/login  admin/admin

后台支持一键生成静态站点

然后去把 {user.home}/site_html 里的所有文件夹及文件上传到你的FTP空间。 
后台支持配置你的FTP，让java代码帮你上传。
像这个样子的静态站点 http://javablog.net
搜索功能没有，会跳转到某个网站。

### 03 git静态部署方式

跟02一样，先生成好静态网站。
然后git push你的git帐号。 
像这个一样 https://daodaovps.github.io/ 





------------


##后台截图

![](http://ww1.sinaimg.cn/mw1024/006qgpQvgw1f7m7e9z770j30mt0h2n0s.jpg)

![](http://ww3.sinaimg.cn/mw1024/006qgpQvgw1f7m7apdzeaj311c0m0wgw.jpg)

![](http://ww3.sinaimg.cn/mw1024/006qgpQvgw1f7m7apzaxdj31160foq9f.jpg)

![](http://ww2.sinaimg.cn/mw1024/006qgpQvgw1f7m7aqgji6j311h0eu0uc.jpg)

![](http://ww2.sinaimg.cn/mw1024/006qgpQvgw1f7m7ar72r7j311d0ma0vf.jpg)

![](http://ww3.sinaimg.cn/mw1024/006qgpQvgw1f7m7ashpyvj311j0cqjtg.jpg)

![](http://ww1.sinaimg.cn/mw1024/006qgpQvgw1f7m7at7ky5j311b0m8tc7.jpg)

------------


##前台截图


![](http://ww3.sinaimg.cn/mw1024/006qgpQvgw1f7m8y3gmypj30ah0iemym.jpg)

![](http://ww1.sinaimg.cn/mw1024/006qgpQvgw1f7m7au2njcj30r20m3tcg.jpg)

![](http://ww2.sinaimg.cn/mw1024/006qgpQvgw1f7m7aum8izj30sc0n7tbs.jpg)

![](http://ww4.sinaimg.cn/mw1024/006qgpQvgw1f7m7b2g0xcj30r70iaac5.jpg)

![](http://ww2.sinaimg.cn/mw1024/006qgpQvgw1f7m7b2y5mcj30rb0kjgpj.jpg)

![](http://ww3.sinaimg.cn/mw1024/006qgpQvgw1f7m7b3v8ifj30po0mqq7w.jpg)





