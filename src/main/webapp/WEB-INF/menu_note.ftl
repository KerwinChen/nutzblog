<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="/static/img/favicon.ico?v=${(obj.version)!''}">
    <title>${(obj.book._booktitle)!''} - ${(obj.site_name)!''}</title>

    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/tutorial-page.css?v=${(obj.version)!''}" rel="stylesheet">


    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="/static/js/html5shiv.min.js"></script>
    <script src="/static/js/respond.min.js"></script>
    <![endif]-->

    <!--  代码高亮 -->
    <link rel="stylesheet" href="/static/js/highlight/prism.css?v=${(obj.version)!''}"/>
    <script src="/static/js/highlight/prism.js?v=${(obj.version)!''}"></script>
<#--<link href="/static/js/google-code-prettify/prettify.css" type="text/css" rel="stylesheet"/>-->
<#--<script src="/static/js/google-code-prettify/prettify.js"></script>-->


</head>
<body class="tutorial-single" data-spy="scroll">

<#include "/index_head.ftl">


<div class="container-fluid">


    <div class="row">

        <div class="col-md-8  col-md-offset-2  col-xs-12 ">
            <div class="wrapper layout-wrapper">

                <div class="row">
                    <div class="col-md-12">
                        <h3><a href="/book/${obj.book._id}/${obj.book._booktitleen}.html">${obj.book._booktitle}</a></h3>
                    </div>
                </div>

                <!--   开始  中间内容foot -->
                <div class="tutorial-footer">

                <#--图书目录 开始-->
                <#if  (obj.book._id) != 0 >
                    <div class="menu_warp">
                    <#--三列，不确定行数，每一行的高度用最高的那一列-->
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-12 ">
                                    <p class="bg-warning  menu_title"><a
                                            href="/book/${(obj.book._id)}/${(obj.book._booktitleen)}.html">目录</a></p>
                                </div>
                            </div>
                        ${(obj.menu)!''}
                        </div>
                    </div>
                </#if>
                <#--图书目录  结束-->


                <#--头像+分享   开始-->
                    <div class="tutorial-footer-details">
                        <!--  用户头像 -->
                        <div class="section-content tutorial-contributors one">
                            <div class="tutorial-footer-user">
                                <a href="javascript:void(0)">
                                    <img alt="manicas" class="avatar avatar-small" height="80"
                                         src="/images/${obj.admin_photo}"
                                         width="80"></a>
                                <div class="user-info">
                                    <a href="javascript:void(0)">${obj.admin}</a>
                                </div>
                            </div>
                        </div>
                        <!-- 三个按钮 -->
                        <div class="share">
                        <#include "/common/share_baidu.ftl">
                        </div>
                    </div>
                <#--头像+分享   结束-->
                <#--评论区域-->
                    <div id="comments">
                    <#include  "/common/comment.ftl">
                    </div>
                <#--评论区域结束-->

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
        //carousel-inner
        $(".tutorial-content img").addClass("img-responsive img-rounded");

        $("#btn_show_series").click(function () {
            $(".tutorial-series").slideToggle();
        });
        $("#btn_hide_series").click(function () {
            $(".tutorial-series").slideUp();
        });


    });


</script>
</body>
</html>
