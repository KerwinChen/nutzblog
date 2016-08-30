<html lang="en">
<#include "common/header.ftl">
<#--上传组件-->
<link href="/adm/assets/js/jquery-file-upload/jquery.fileupload.css" rel="stylesheet">
<script src="/adm/assets/js/jquery-file-upload/jquery.ui.widget.js"></script>
<script src="/adm/assets/js/jquery-file-upload/jquery.iframe-transport.js"></script>
<script src="/adm/assets/js/jquery-file-upload/jquery.fileupload.js"></script>
<script src="/adm/assets/js/jquery-file-upload/upload-base.js"></script>
<link rel="stylesheet" href="/adm/assets/css/bootstrap-datepicker3.min.css"/>

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
                        <a href="#">博客管理</a>
                    </li>
                    <li class="active">博客列表</li>
                </ul>
            </div>
            <div class="page-content">
                <div class="page-header">
                    <h1>站点设置</h1>
                </div>
                <div class="row">
                    <div class="col-md-12 form-horizontal">
                        <h4 class="header blue bolder smaller">站点属性</h4>

                    <#--site_name-->
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"
                                   for="form-field-facebook">博客名称</label>

                            <div class="col-sm-9">
                                <input type="text" value="" id="site_name">
                            </div>
                        </div>

                    <#--选择,上传logo   site_logo-->
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"
                                    >上传Logo</label>

                            <div class="col-sm-9">
                                <div id="fileupload_logo">
                        <span class="btn btn-success btn-sm fileinput-button">
                            <i class="glyphicon glyphicon-plus"></i>
                            <span>上传</span>
                            <!-- The file input field used as target for the file upload widget -->
                            <input class="btn btn-primary" id="fileupload" type="file" name="f" multiple="">
                        </span>
                                    <button id="btn_selectimg_logo" type="button" class="btn btn-sm  btn-info">选择图片
                                    </button>
                                    <br>

                                    <div id="progress" class="progress" style="display: none;margin: 2px auto">
                                        <div class="progress-bar progress-bar-success"></div>
                                    </div>
                                    <div class="showimg" id="imgid_logo" imgid="${(obj.item._toppic)!""}"
                                         style="margin: 2px auto">
                                        <a target="_blank"
                                           href="/view/${(obj.item._toppic)!""}/">${(obj.item._toppic)!""}</a>
                                    </div>
                                </div>
                            </div>
                        </div>

                    <#--site_fav-->
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"
                                    >上传favicon.ico</label>

                            <div class="col-sm-9">
                                <div id="fileupload_fav">
                        <span class="btn btn-success btn-sm fileinput-button">
                            <i class="glyphicon glyphicon-plus"></i>
                            <span>上传</span>
                            <!-- The file input field used as target for the file upload widget -->
                            <input class="btn btn-primary" id="fileupload" type="file" name="f" multiple="">
                        </span>
                                    <button id="btn_selectimg_fav" type="button" class="btn btn-sm  btn-info">选择图片
                                    </button>
                                    <br>

                                    <div id="progress" class="progress" style="display: none;margin: 2px auto">
                                        <div class="progress-bar progress-bar-success"></div>
                                    </div>
                                    <div class="showimg" id="imgid_fav" imgid="${(obj.item._toppic)!""}"
                                         style="margin: 2px auto">
                                        <a target="_blank"
                                           href="/view/${(obj.item._toppic)!""}/">${(obj.item._toppic)!""}</a>
                                    </div>
                                </div>
                            </div>
                        </div>

                    <#--site_createtime-->
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"
                                   for="form-field-facebook">建站时间</label>
                            <div class="col-sm-9">
                                <div class="input-group">
                                    <input class="form-control date-picker" id="site_createtime" type="text"
                                           data-date-format="yyyy-mm-dd"/>
                                    <span class="input-group-addon">
																		<i class="fa fa-calendar bigger-110"></i>
																	</span>
                                </div>
                            </div>
                        </div>



                    </div>
                </div>
            </div>
        </div>
    </div>
<#include "common/footer.ftl">
</div>

<#include  "common/endjs.ftl">


<script>
    $(function () {
        //设置哪个 btn_upload 为上传控件
        fileupload("fileupload_logo");

        //弹出选择图片 iframe
        $('#btn_selectimg_logo').bind('click', function () {
                    layer.open({
                        title: "选择图片",
                        type: 2,
                        area: ['900px', '600px'],
                        offset: ['8%', ''],
                        content: ['/adm/upload/selectimg?callbackid=imgid_logo', 'no']
                    });
                }
        );

        //设置哪个 btn_upload 为上传控件
        fileupload("fileupload_fav");

        //弹出选择图片 iframe
        $('#btn_selectimg_fav').bind('click', function () {
                    layer.open({
                        title: "选择图片",
                        type: 2,
                        area: ['900px', '600px'],
                        offset: ['8%', ''],
                        content: ['/adm/upload/selectimg?callbackid=imgid_fav', 'no']
                    });
                }
        );
        $('#site_createtime').datepicker({
            autoclose: true,
            todayHighlight: true
        })

//        $("#site_createtime").datepicker({
//            autoclose: true,
//            todayHighlight: true
//        });


    });

</script>

</body>
</html>