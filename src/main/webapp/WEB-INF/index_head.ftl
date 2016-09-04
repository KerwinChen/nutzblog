<script>
    if (top != self) {
        location.href = "about:blank";
    }
</script>
<nav class="navbar navbar-static-top bs-docs-nav" style="background: #f8f8f8; padding: 15px 0 15px 0" id="top"
     role="banner">
    <div class="container-fluid">
        <div class=" col-md-8 col-md-offset-2">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                        aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            <#--<a class="navbar-brand" href="#">Project name</a>-->
                <a title="${obj.site_name}" href="/" class="navbar-brand logo"
                   style="background: url('/images/${obj.site_logo}') no-repeat;">${obj.site_name}</a>
                <ul class="header nav navbar-nav navbar-right">
                    <li><a href="/">首页</a></li>
                    <li><a href="/archive.html">归档</a></li>
                    <li><a href="/tags.html">标签</a></li>
                    <li><a href="/me.html">ME</a></li>
                </ul>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <div class="input-group col-md-4 header nav navbar-nav navbar-right">
                    <form class="navbar-form navbar-right" method="get" action="/search">
                        <input value="${(obj.q)!''}" id="q" name="q" type="text" class="form-control" placeholder="Search">
                        <span class="input-group-btn">
                        <button class="btn btn-default" type="submit">搜索</button>
                        </span>
                    </form>
                </div>
            </div>
        </div>
    </div>
</nav>