<html lang="en">
<#include "common/header.ftl">
<body class="no-skin">
<#include "common/navbar.ftl">

<div class="main-container ace-save-state" id="main-container">
<#include "common/sidebar.ftl">
    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="/adm/index">Home</a>
                    </li>
                    <li>
                        <a href="#">首页</a>
                    </li>
                <#--<li class="active">博客列表</li>-->
                </ul>
            </div>
            <div class="page-content">
                <!-- /.ace-settings-container -->
                <div class="page-header">
                    <h1>
                        快捷链接
                        <small>
                            <i class="ace-icon fa fa-angle-double-right"></i>
                        </small>
                    </h1>
                </div>
                <!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12" style="font-size: 16px">

                        <a target="_blank" href="/log">查看日志</a> <br><br>
                        <a target="_blank" href="/createhtml_site">一键生成HTML</a> <br><br>
                        <a href="">上传所有的HTML</a> <br><br>
                        <#--<a href="">清空FTP所有HTMl文件</a> <br><br>-->
                        <#--<a href="">最后一次编辑的文件</a><br><br>-->

                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.page-content -->
        </div>
    </div>
<#include "common/footer.ftl">
</div>

<#include  "common/endjs.ftl">
</body>
</html>