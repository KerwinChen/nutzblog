<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="/static/img/favicon.ico?v=${(ojb.version)!''}" ">
    <title>关于本站 - ${(obj.site_name)!''}</title>

    <link href="/static/css/bootstrap.min.css?v=${(ojb.version)!''}" " rel="stylesheet">
    <link href="/static/css/tags.css?v=${(ojb.version)!''}" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="/static/js/html5shiv.min.js"></script>
    <script src="/static/js/respond.min.js"></script>
    <![endif]-->

    <script src="/static/js/google-code-prettify/prettify.js"></script>
    <link href="/static/js/google-code-prettify/prettify.css" rel="stylesheet">
    <style>
        pre.prettyprint {
            padding: 15px;
            border: 1px solid #e1e1e8;
        }
    </style>

</head>
<body>
<#include "/index_head.ftl">

<div class="container-fluid">
    <div class="row">
        <div class="col-md-8 col-md-offset-2 col-xs-12">

            <h3>关于本站</h3>

            <div>
                ${(obj.site_aboutme)!''}
            </div>
        </div>

    </div>


    <div class="row">

        <div class="col-md-8 col-md-offset-2 col-xs-12">
            <div id="comments">
            <#include  "/common/comment.ftl">
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

    var currhrf = window.location.href;
    //nav的active
    $("nav ul li a.active").removeClass("active");
    if (currhrf.indexOf("me.html") > 0) {
        $("nav ul li:nth-child(4) a").addClass("active");
    }

</script>

</body>
</html>
