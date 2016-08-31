<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="/static/img/favicon.ico?v=${obj.version}">
    <title>历史归档 - ${obj.site_name}</title>
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/archive.css?v=${obj.version}" rel="stylesheet">

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

            <div class="archive_title">
                <h3>历史归档</h3>

                <div>
                    <p>建站${obj.build_long}天，共计${obj.allcount}篇文章。建站时间：${obj.build_begindate} 。</p>
                </div>
            </div>

            <div class="archive_warp">
            ${obj.datas}
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
        if (currhrf.indexOf("archive.html") > 0) {
            $("nav ul li:nth-child(2) a").addClass("active");
        }
    });
</script>

</body>
</html>
