<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="/view/${obj.site_fav}/?v=${obj.version}">
    <meta name="description"
          content="Java核心技术 - 提供简单独特的Java开发教程 ."/>

    <meta name="keywords"
          content="J2EE, Java, Android, Hibernate, Spring,Hadoop,Hive,Spark,Linux,JavaScript, Maven, jQuery, Java 教程, Hibernate 教程, Spring 教程, Maven 教程, jQuery 教程, Android 教程 ,大数据教程."/>

    <#--<link rel='next' href='http://javacore.cn/index/2.html'/>-->


    <title>${obj.site_name} - 提供简单独特的Java开发教程</title>

    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/index.css?v=${obj.version}" rel="stylesheet">

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

        <div class="col-md-8 col-md-offset-2 col-xs-12 ">

            <div class="quarter col-md-3 col-xs-3  ">
                <div id="hp-box-1">
                    <a target="_self" href="/filter/page/book/1.html">
                        <span class="bucket-icon">
                            <img class="center-block  img-responsive img-rounded"
                                 src="/static/img/nav_01.png?v=${obj.version}">
                        </span>

                        <h3>分类</h3>
                    </a></div>
            </div>

            <div class="quarter col-md-3 col-xs-3  ">
                <div id="hp-box-2">
                    <a target="_blank" href="https://disqus.com/home/forums/javacorecn/">
                        <span class="bucket-icon">
                            <img class="center-block  img-responsive img-rounded"
                                 src="/static/img/nav_02.png?v=${obj.version}">
                        </span>

                        <h3>提问</h3>
                    </a>
                </div>
            </div>

            <div class="quarter col-md-3  col-xs-3  ">
                <div id="hp-box-3">
                    <a target="_self" href="/proj/1.html">
                        <span class="bucket-icon">
                            <img class="center-block  img-responsive img-rounded"
                                 src="/static/img/nav_03.png?v=${obj.version}">
                        </span>

                        <h3>项目</h3>
                    </a>
                </div>
            </div>

            <div class="quarter col-md-3 col-xs-3  ">
                <div id="hp-box-4">
                    <a target="_self" href="/tags.html">
                        <span class="bucket-icon">
                            <img class="center-block  img-responsive img-rounded"
                                 src="/static/img/nav_04.png?v=${obj.version}">
                        </span>

                        <h3>标签</h3>
                    </a>
                </div>
            </div>

        </div>
    </div>


    <div class="row">
        <div class="col-md-8  col-md-offset-2  col-xs-12  ">

        </div>
    </div>

<#--中间区域部分-->

    <div class="row">
        <div class="col-md-8  col-md-offset-2  col-xs-12  ">
            <div id="feed-container">

                <div id="feed">
                    <div class="filter-bar">

                        <div id="fadeable">
                            <ul class="filters">
                                <li class="seris_li0">
                                    <a class="active" href="javascript:void(0)">文章列表 (${obj.pagescount})</a>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <div id="filter-objects">
                        <ul id="ul_datalist">

                        <#list  obj.datas  as value>
                            <li class="li_index">

                                <h3>
                                    <a class="page_title" target="_blank"
                                       href="${value._serieshref}"> ${value._title}</a>
                                    <a class="series" href="javascript:void(0)" title=""
                                       style="${value._seris_show}">
                                    ${value._seris_tag}</a>
                                </h3>

                                <span class="difficulty-level Beginner">初级</span>

                                <div class="meta">

                                    <div class="points">3
                                        <span class="icon-upvote-heart-full"></span>
                                    </div>

                                <span class="author">
                                          by <a href="#">${value.username}</a>
                                  </span>

                                    <#if value.tagstr?length gt 0>
                                        <span class="meta-value">标签
                                        ${value.tagstr}
                                            </span>
                                    </#if>

                                    <span class="time">
                                    ${value.updatetime?string('yyyy-M-d')}
                                    </span>
                                </div>

                            </li>
                        </#list>
                        </ul>
                    </div>

                    <div id="pagination">
                        <div class="pagination hidden-xs" id="div_pager">
                        ${obj.pages}
                        </div>
                        <div class="pagination visible-xs-inline-block" id="div_pager_min">
                        ${obj.pages_min}
                        </div>


                    </div>


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

        var host = window.location.host;
        host = "http://" + host + "/";
        if (host == currhrf) {
            console.log("首页");
            $("nav ul li:nth-child(1) a").addClass("active");
        }
    });
</script>

</body>
</html>
