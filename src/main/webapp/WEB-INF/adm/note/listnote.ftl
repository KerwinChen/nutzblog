<html lang="en">
<#include "../common/header.ftl">
<body class="no-skin">
<#include "../common/navbar.ftl">

<div class="main-container ace-save-state" id="main-container">
<#include "../common/sidebar.ftl">
    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="/adm/index">Home</a>
                    </li>
                    <li>
                        <a href="#">系列教程</a>
                    </li>
                    <#--<li class="active">博客列表</li>-->
                </ul>
            </div>
            <div class="page-content">

                <div class="row">
                    <div class="col-md-12">
                        <form class="form-inline">
                            <div class="form-group">
                                <input type="text" class="form-control input-sm" id="txt_q" placeholder="标题">
                            </div>
                            <button id="btn_q" type="button" class="btn btn-info btn-xs">
                                搜索
                            </button>
                        </form>
                    </div>
                </div>




            </div>
        </div>
    </div>
<#include "../common/footer.ftl">
</div>

<#include  "../common/endjs.ftl">
</body>
</html>