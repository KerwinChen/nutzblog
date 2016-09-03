<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="/static/img/favicon.ico?v=${(obj.version)!''}">
    <title>标签 -  ${(obj.site_name)!''}</title>
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/tags.css?v=${(obj.version)!''}" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="/static/js/html5shiv.min.js"></script>
    <script src="/static/js/respond.min.js"></script>
    <![endif]-->


</head>
<body>
<#include "/index_head.ftl">
<div class="container-fluid">
    <div class="row">
        <div class="col-md-8 col-md-offset-2 col-xs-12">

            <div class="tags_warp">

                <div class="container-fluid">

                    <div class="row">

                        <div class="col-md-12">
                            <h3>标签树</h3>

                            <p>这里列出了常用的标签，你可以选择喜欢的标签进行关注，还可以查看 热门标签</p>
                        </div>

                    </div>
                <#if  (obj.s1)??>
                    <div class="row">
                        <div class="col-md-4">
                        ${obj.s1}
                        </div>

                        <div class="col-md-4">
                        ${obj.s2}
                        </div>
                        
                        <div class="col-md-4">
                        ${obj.s3}
                        </div>

                    </div>
                <#else>
                    暂无数据
                </#if>
                </div>
            </div>
        </div>
    </div>
</div>

<footer id="footer">
<#include  "/index_foot.ftl">
</footer>


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="/static/js/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/static/js/bootstrap.min.js"></script>

<script>
    $(function () {
        var currhrf = window.location.href;
        console.log(currhrf);
        $("nav ul li a.active").removeClass("active");
        if (currhrf.indexOf("tags.html") > 0) {
            $("nav ul li:nth-child(3) a").addClass("active");
        }
    });
</script>
</body>
</html>
