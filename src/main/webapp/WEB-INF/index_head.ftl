<script>
    if (top != self) {
        location.href = "about:blank";
    }
</script>
<nav class="navbar navbar-static-top bs-docs-nav"  style="background: #f8f8f8; padding: 15px 0 15px 0" id="top" role="banner">
    <div class="container-fluid">
        <div class=" col-md-8 col-md-offset-2">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            <#--<a class="navbar-brand" href="#">Project name</a>-->
                <a title="Java核心技术 | JavaCore.CN" href="http://javacore.cn/" class="navbar-brand logo"
                        style="background: url('/view/${obj.site_logo}/') no-repeat;">JavaCore.CN</a>
                <ul class="header nav navbar-nav navbar-right">
                    <li><a href="http://javacore.cn/">首页</a></li>
                    <li><a href="http://javacore.cn/archive.html">归档</a></li>
                    <li><a href="http://javacore.cn/tags.html">标签</a></li>
                    <li><a href="http://javacore.cn/me.html">ME</a></li>
                </ul>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <div class="input-group col-md-4 header nav navbar-nav navbar-right">
                    <form class="navbar-form navbar-right" method="get" action="http://s.javacore.cn:8989/search">
                        <input  value="${q!''}" id="q" name="q" type="text" class="form-control" placeholder="Search">
                        <span class="input-group-btn">
                        <button class="btn btn-default" type="submit">搜索</button>
                        </span>
                    </form>
                </div>
            </div>
        </div>
    </div>
</nav>