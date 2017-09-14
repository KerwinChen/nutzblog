<script>
    if (top != self) {
        location.href = "about:blank";
    }
</script>

<header class="navbar navbar-static-top bs-docs-nav" style="background: #f8f8f8; padding: 15px 0 15px 0" id="top"
        role="banner">
    <div class="container-fluid">
        <div class=" col-md-8 col-md-offset-2">
            <div class="navbar-header ">
                <button class="navbar-toggle collapsed" type="button" data-toggle="collapse"
                        data-target=".bs-navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a title="${obj.site_name}" href="http://javablog.net/" class="navbar-brand logo"
                   style="background: url('/images/${obj.site_logo}') no-repeat;">${obj.site_name}</a>
            </div>
            <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
                <ul class="nav header navbar-nav">
                    <li><a href="http://javablog.net/">首页</a></li>
                    <li><a href="http://javablog.net/archive.html">归档</a></li>
                    <li><a href="http://javablog.net/tags.html">标签</a></li>
                    <li><a href="http://javablog.net/me.html">ME</a></li>
                </ul>
                <form target="_self" method="get" action="https://s.javablog.net/search"
                <#--<form target="_self" method="get" action="/search"-->
                      class="navbar-form navbar-right">
                    <input value="${(obj.q)!''}" id="q" name="q" type="text" class="form-control"
                           placeholder="Search">
                </form>
            <#--<form target="_self" class="navbar-form navbar-right" method="get"-->
            <#--action="http://s.javablog.net/search">-->
            <#--<span class="input-group-btn">-->
            <#--<button class="btn btn-default" type="submit">搜索</button>-->
            <#--</span>-->
            <#--</form>-->

            </nav>
        </div>
    </div>
    </div>
</header>



