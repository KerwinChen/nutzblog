<html lang="en">
<#include "common/header.ftl">
<#--tools-->
<script src="/adm/assets/js/autosize.min.js"></script>
<script src="/adm/assets/js/jquery.inputlimiter.min.js"></script>
<script src="/adm/assets/js/bootstrap-tag.min.js"></script>

<#--对话框-->
<script src="/adm/assets/js/dialog/artDialog.js?skin=chrome"></script>
<script src="/adm/assets/js/dialog/plugins/iframeTools.js"></script>

<#--上传组件-->
<link href="/adm/assets/js/jquery-file-upload/jquery.fileupload.css" rel="stylesheet">
<script src="/adm/assets/js/jquery-file-upload/jquery.ui.widget.js"></script>
<script src="/adm/assets/js/jquery-file-upload/jquery.iframe-transport.js"></script>
<script src="/adm/assets/js/jquery-file-upload/jquery.fileupload.js"></script>
<script src="/adm/assets/js/jquery-file-upload/upload-base.js"></script>

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
                        <a href="/adm/wblog">Home</a>
                    </li>
                    <li>
                        <a href="#">博客管理</a>
                    </li>
                    <li class="active">写博客</li>
                </ul>
            </div>
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <form id="save_single_form" method="post" action="" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-1 control-label">标题</label>
                                <input type="hidden" value="${obj.item._id}" id="_id">

                                <div class="col-sm-11">
                                    <input type="input" class="form-control" name="_title" id="_title" class="required"
                                           value="${obj.item._title!''}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-1 control-label">简短名</label>

                                <div class="col-sm-11">
                                    <input type="input" class="form-control" name="_titleinlogo" id="_titleinlogo"
                                           class="required"
                                           value="${obj.item._titleinlogo!''}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-1 control-label">摘要</label>

                                <div class="col-sm-11">
                                <textarea class="form-control" rows="3" name="_showintro" id="_showintro"
                                          class="required">${obj.item._showintro!""}</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-1 control-label">选择标签</label>

                                <div class="col-sm-11">
                                    <input type="text" name="tags" id="form-field-tags"
                                           value="${obj.item._tags!""}"
                                           placeholder="Enter tags ..."/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-1 control-label">置顶大图</label>

                                <div class="col-sm-11" id="fileupload_group">
                        <span class="btn btn-success btn-sm fileinput-button">
                            <i class="glyphicon glyphicon-plus"></i>
                            <span>上传</span>
                            <!-- The file input field used as target for the file upload widget -->
                            <input class="btn btn-primary" id="fileupload" type="file" name="f" multiple="">
                        </span>
                                    <button id="btn_selectimg" type="button" class="btn btn-sm  btn-info">选择图片</button>
                                    <br>
                                    <div id="progress" class="progress" style="display: none;margin: 2px auto">
                                        <div class="progress-bar progress-bar-success"></div>
                                    </div>
                                    <div class="showimg" id="imgid" imgid="${obj.item._toppic!""}" style="margin: 2px auto">
                                        <a target="_blank" href="${obj.item._toppic!""}">${obj.item._toppic!""}</a>
                                    </div>
                                </div>
                            </div>

                        <#--内容区域-->
                            <div class="form-group">
                                <div id="test-editormd" style="z-index: 1">
                                    <textarea style="display:none;">${obj.item._content_markdown!""}</textarea>
                                </div>
                            </div>


                            <div class="form-group">
                                <div class="col-sm-11 col-sm-offset-1">
                                    <button id="btn_submit" type="button" class="btn btn-default">提交</button>
                                </div>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
            <!-- /.page-content -->
        </div>
    </div>
<#include "common/footer.ftl">
</div>

<#include  "common/endjs.ftl">


<script>

    var manyfile = false;
    function fileupload(id) {
        $("#" + id).find("input[type='file']").fileupload({
            url: "/adm/upload",
//            dataType: 'json', //如果不返回json数据，需要注释掉。
            add: function (e, data) {
                $("#" + id).find(".showimg").attr("imgid", "");
                var uploadErrors = [];
                var filename = data.originalFiles[0]['name'];
                if (filename.substring(filename.length - 3, filename.length) != "jpg" && filename.substring(filename.length - 3, filename.length) != "png") {
                    uploadErrors.push('只能上传jpg,png后缀的文件');
                }

                if (!manyfile) {
                    if (data.originalFiles.length > 1) {
                        uploadErrors.push('只能上传1个文件');
                        manyfile = true;
                    }
                }

                if (data.originalFiles[0]['size'] && data.originalFiles[0]['size'] > 1024 * 1024 * 10) {
                    uploadErrors.push('上传文件不能大于10MB');
                }

                if (uploadErrors.length > 0) {
                    alert(uploadErrors.join("\n"));
                }
                else {
                    $("#" + id).find('.progress').show();
                    $("#" + id).attr("disabled", "disabled");
                    data.submit();
                }
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $("#" + id).find('.progress-bar').css(
                        'width',
                        progress + '%'
                ).text(progress + '%');
            },
            done: function (e, data) {
                data = data["result"];
                var imgid = data["imgid"];
                $("#" + id).find(".showimg").html("<a  target='_blank' href=\"/down/" + imgid + "/\">查看</a>");
                $("#" + id).find(".showimg").attr("imgid", imgid);
                $("#" + id).removeAttr("disabled");
                setTimeout("hideprocess(\"" + id + "\")", 3000);


//                var win = art.dialog.top;
//                win.artDialog.list['dia_ad_recommend'].size(360, 650).position("50%", "50%");

            }
        });
    }


    function hideprocess() {
        //上传完毕，5秒钟后消失。并且使得进度条
        $('#progress').hide();
        //进度值减为0
        $('.progress-bar').css(
                'width', '0%'
        );
    }


    function tag_init() {
        var tag_input = $('#form-field-tags');
        try {
            var tags = new Array();
        <#list  obj.tags as item>
        <#--tags.push("${item._pname} - ${item._name}");-->
            tags.push("${item._name}");
        </#list>


            tag_input.tag(
                    {
                        placeholder: tag_input.attr('placeholder'),
                        source: tags,//defined in ace.js >> ace.enable_search_ahead
                        /**
                         //or fetch data from database, fetch those that match "query"
                         source: function(query, process) {
                                      $.ajax({url: 'remote_source.php?q='+encodeURIComponent(query)})
                                      .done(function(result_items){
                                        process(result_items);
                                      });
                                    }
                         */
                    }
            )

            //programmatically add/remove a tag
//            var $tag_obj = $('#form-field-tags').data('tag');
//            $tag_obj.add('Programmatically Added');
//            var index = $tag_obj.inValues('some tag');
//            $tag_obj.remove(index);

        }
        catch (e) {
            //display a textarea for old IE, because it doesn't support this plugin or another one I tried!
            tag_input.after('<textarea id="' + tag_input.attr('id') + '" name="' + tag_input.attr('name') + '" rows="3">' + tag_input.val() + '</textarea>').remove();
            //autosize($('#form-field-tags'));
        }
    }
    $(function () {

        tag_init();

        //设置哪个 btn_upload 为上传控件
        fileupload("fileupload_group");

    });


</script>

</body>
</html>