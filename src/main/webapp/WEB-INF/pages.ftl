<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="Keywords" content="${(obj.item._title)!''},${(obj.item._titleen)!''}"/>
    <meta name="Description" content="${(obj.item._title)!''},${(obj.item._showintro)!''}"/>

    <link rel="icon" href="/static/img/favicon.ico?v=${(obj.version)!''}">
    <title>${(obj.item._title)!''} - ${(obj.site_name)}</title>

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
<#--<link href="/static/js/google-code-prettify/prettify.css" type="text/css" rel="stylesheet"/>-->
<#--<script src="/static/js/google-code-prettify/prettify.js"></script>-->


</head>
<body class="tutorial-single" data-spy="scroll">

<#include "/index_head.ftl">


<div class="container-fluid">

<#if (obj.showadm)=="true">
    <div class="row">
        <div class="col-md-8  col-md-offset-2  col-xs-12 ">
            <a href="/adm/seris_mgr/showaddup_inlist/?book_id=${(obj.book._id)!"0"}&seris_id=${obj.s._id}&single_id=${(obj.item._id)!''}">编辑</a>
            <a href="javascript:del(${(obj.item._id)!''})">删除</a>
        </div>
    </div>
</#if>

    <div class="row">

        <div class="col-md-8  col-md-offset-2  col-xs-12 ">
            <div class="wrapper layout-wrapper">
                <div class="section-content content">
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
                    <#if  (obj.tags)?? && (obj.tags)?length gt 0>
                        <span class="meta-label">Tags: </span>
                                <span class="meta-value"> ${(obj.tags)}</span>
                    </#if>
                    </span>

                <#--文章索引 list-->
                    <div class="tutorial-series-container collapsed">
                        <div class="section-content">
                            <h2 class="tutorial-series-heading">Tutorial Series</h2>

                            <div class="tutorial-series-toggle">
                                This tutorial is part ${(obj.start)} of ${(obj.end)} in the series:
                                <a id="btn_show_series" data-reveal="fast"
                                   href="javascript:void(0)">${(obj.s._seristitle)!''}</a>
                            </div>

                            <div id="tutorial_series_12" class="tutorial-series"
                            <#if  (obj.book._id) != 0 >
                                 style="display:none;"
                            </#if>
                                    >
                                <a id="btn_hide_series" class="icon-close" href="javascript:void(0)"></a>

                                <h3 class="series-title"><a href="#">${(obj.s._seristitle)}</a></h3>

                                <div class="description">
                                ${(obj.s._serisintro)}
                                </div>


                                <ol class="tutorial-series-list">
                                <#list  obj.singlelist as itemin>
                                    <li <#if  itemin._id ==  obj.item._id>
                                            class="active"
                                    </#if>>
                                        <h2>
                                            <a href="/pages/${itemin._id}/${itemin._titleen}.html">${itemin._title}</a>
                                        </h2>

                                        <div class="feedable-date">${itemin.ut?string("yyyy-MM-dd")}</div>
                                        <div class="meta">
                                            <div class="points">
                                                <span class="icon-upvote-heart-small"></span>
                                                52
                                            </div>
                                            <div class="response-count">
                                                <span class="icon icon-new-comment"></span>
                                                156
                                            </div>
                                            <span class="author">By <a
                                                    href="javascript:void(0)">${itemin.username}</a></span>
                                        </div>
                                        <div class="arrow-left"></div>
                                    </li>
                                </#list>
                                </ol>
                            </div>


                        </div>
                    </div>


                <#--正文部分-->
                    <div class="content-body tutorial-content">
                        <h3 id="introduction">摘要</h3>
                        <p>${(obj.item._showintro)!""}</p>
                    ${(obj.item._content_html)!""}
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
                                    <p class="bg-warning  menu_title">
                                        <a href="/book/${obj.book._id}/${(obj.book._booktitleen)}.html">${(obj.book._booktitle)}</a>
                                    </p>
                                </div>
                            </div>
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

                    <div class="googlead">

                    </div>

                <#--头像+分享   开始-->
                    <div class="tutorial-footer-details">

                        <!--  用户头像 -->
                        <div class="section-content tutorial-contributors one">
                            <div class="tutorial-footer-user">
                                <a href="javascript:void(0)">
                                    <img alt="manicas" class="avatar avatar-small" height="80"
                                         src="/images/${obj.userphoto}"
                                         width="80"></a>

                                <div class="user-info">
                                    <a href="javascript:void(0)">${obj.username}</a>
                                </div>
                            </div>
                        </div>

                        <!-- 三个按钮 -->
                        <div class="share">
                        <#include "/common/share.ftl">
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

    <#-- 右侧导航部分  开始-->
        <div class="col-md-2  hidden-xs hidden-sm">

            <div class="outside_viewport">
                <div class="table-of-contents  tocify open" style="top:100px">
                    <ul id="myaffix" id="tocify-header0" class="nav  affix-top"
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
        //carousel-inner
        $(".tutorial-content img").addClass("img-responsive img-rounded");

        $("#btn_show_series").click(function () {
            $(".tutorial-series").slideToggle();
        });
        $("#btn_hide_series").click(function () {
            $(".tutorial-series").slideUp();
        });


    });

    $('#myaffix ').affix({
        offset: {
            top: 280
            , bottom: function () {
                return (this.bottom = $('.tutorial-footer').outerHeight(true) + $('#footer').outerHeight(true))
            }
        }
    });


    function del(id) {

        if (confirm("确定删除吗?")) {
            $.get("http://write.javacore.cn:9999/adm/single_mgr/del/?id=" + id + "", function (data) {
//                alert("删除结果:" + data);
                window.location.href = "/";
            });
        }
    }


</script>
</body>
</html>
