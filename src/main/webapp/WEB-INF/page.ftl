<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <meta name="Keywords" content="${(obj.item._title)!''},${(obj.item._titleen)!''}"/>
    <meta name="Description" content="${(obj.item._title)!''},${(obj.item._showintro)!''}"/>

    <link rel="icon" href="/images/${obj.site_fav}?v=${(obj.version)!''}">
    <title>${(obj.item._title)!''} - ${(obj.site_name)!''}</title>

    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/tutorial-page.css?v=${(obj.version)!''}" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="/static/js/html5shiv.min.js"></script>
    <script src="/static/js/respond.min.js"></script>
    <![endif]-->


    <!--  代码高亮 -->
    <link rel="stylesheet" href="/static/js/highlight/prism-okaidia.css?v=${(obj.version)!''}"/>
    <script src="/static/js/highlight/prism.js?v=${(obj.version)!''}"></script>


</head>
<body data-spy="scroll">

<#include "/index_head.ftl">

<div class="container-fluid">


<#if obj.showadm=="true">
    <div class="row">
        <div class="col-md-8  col-md-offset-2  col-xs-12 ">
            <a href="/adm/wblog/?_id=${(obj.item._id)}">编辑</a>
            <a href="javascript:del(${(obj.item._id)})">删除</a>
        </div>
    </div>
</#if>

<#--中间区域部分-->
    <div class="row">
        <div class="col-md-8  col-md-offset-2   col-xs-12 ">

            <div class="wrapper layout-wrapper">

                <div class="section-content content">

                <#--<img alt="${item._title}" class="tutorial-image"-->
                <#--src="${item._toppic!""}" width="100%" height="300"-->
                <#--title="${item._title!""}"-->
                <#-->-->

                <#--调试模式   -->
                <#--<img alt="java" class="tutorial-image"-->
                <#--src="http://7xkmpo.com1.z0.glb.clouddn.com/f4a7a18b9f8a4a34b43db92200d3c552.jpg" width="100%"-->
                <#--height="300" title="java">-->

                    <div class="col-cv-bg col-cv-bg-shown"
                         style="display:block;left: 0px; top: 0px; width: 100%; height: 300px;background-size:cover;  background-image: url(/images/${(obj.item._toppic)!""}) ;">
                        <div class="col-cv-bg-title">
                        ${(obj.item._titleinlogo)!""}
                        </div>
                    </div>

                    <!--  标题  -->
                    <h1 class="content-title">${(obj.item._title)!""}</h1>
                    <!--  标签 -->
                <span class="meta-section tags">
                <#if  tags?? && tags?length gt 0>
                    <span class="meta-label">Tags: </span>
                    <span class="meta-value">
                    ${obj.tags}
                    </span>
                </#if>
                </span>





                <#--正文部分-->
                    <div class="content-body tutorial-content">
                        <h3 id="introduction">摘要</h3>

                        <p>${(obj.item._showintro)!""}</p>
                    ${(obj.item._content_html)!""}
                    </div>
                </div>


                <!--   开始  中间内容foot -->
                <div class="tutorial-footer">
                    <div class="tutorial-footer-details">

                        <div class="googlead">
                            <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                            <!-- javablog.net -->
                            <ins class="adsbygoogle"
                                 style="display:inline-block;width:728px;height:90px"
                                 data-ad-client="ca-pub-7848078487754242"
                                 data-ad-slot="9015028010"></ins>
                            <script>
                                (adsbygoogle = window.adsbygoogle || []).push({});
                            </script>
                        </div>

                    <#--用户头像部分-->
                        <div class="tutorial-footer-user">
                            <a href="javascript:void(0)">
                                <img alt="manicas" class="avatar avatar-small" height="80"
                                     src="/images/${obj.userphoto}"
                                     width="80">
                            </a>

                            <div class="user-info">
                                <a href="javascript:void(0)">${obj.username}</a>
                            </div>

                        </div>

                        <!--  分享按钮 -->
                        <div class="share">
                        <#include "/common/share_baidu.ftl">
                        </div>

                    <#--评论区域-->
                        <div id="comments">
                        <#include  "/common/comment.ftl">
                        </div>
                    <#--评论区域结束-->


                    </div>
                </div>
            </div>

        </div>


    <#-- 右侧导航部分  开始-->
        <div class="col-md-2  hidden-xs hidden-sm">
            <div class="outside_viewport">
                <div class="table-of-contents  tocify open" style="top:100px">
                    <ul id="myaffix" id="tocify-header0" class="nav  affix-top  tocify-header"
                        data-spy="affix">
                    <#--<li class="side_menutitle"><a href="javascript:void(0)">段落导航</a></li>-->
                    ${(obj.rightnav)!""}
                    </ul>
                </div>

            </div>
        </div>
    <#--右侧导航部分  结束-->

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
//        $(".tutorial-content img").addClass("carousel-inner img-responsive img-rounded");
        $(".tutorial-content img").addClass("img-responsive img-rounded");

    });
    $('#myaffix').affix({
        offset: {
            top: 100
            , bottom: function () {
                return (this.bottom = $('.tutorial-footer').outerHeight(true) + $('#footer').outerHeight(true))
            }
        }
    });

    function del(id) {

        if (confirm("确定删除吗?")) {
            $.get("/adm/single_mgr/del/?id=" + id + "", function (data) {
//                alert("删除结果:" + data);
                window.location.href("/");
            });
        }
    }

</script>


</body>
</html>
