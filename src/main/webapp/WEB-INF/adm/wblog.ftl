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
                                    <input type="text" name="tags" id="form-field-tags" value="Tag Input Control"
                                           placeholder="Enter tags ..."/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-1 control-label">置顶大图</label>

                                <div class="col-sm-11">
                        <span class="btn btn-success btn-sm fileinput-button">
                            <i class="glyphicon glyphicon-plus"></i>
                            <span>上传</span>
                            <!-- The file input field used as target for the file upload widget -->
                            <input class="btn btn-primary" id="fileupload" type="file" name="files[]" multiple="">
                        </span>
                                    <button id="btn_selectimg" type="button" class="btn btn-sm  btn-info">选择图片</button>
                                    <br>

                                    <div id="progress" class="progress" style="display: none;margin-bottom: 0px;">
                                        <div class="progress-bar progress-bar-success"></div>
                                    </div>
                                    <div id="imgid" imgid="${obj.item._toppic!""}">
                                        <a target="_blank"
                                           href="${obj.item._toppic!""}">${obj.item._toppic!""}</a>
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
    $(function () {

        var tag_input = $('#form-field-tags');
        try {
            tag_input.tag(
                    {
                        placeholder: tag_input.attr('placeholder'),
                        //enable typeahead by specifying the source array
                        source: ace.vars['US_STATES'],//defined in ace.js >> ace.enable_search_ahead
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
            var $tag_obj = $('#form-field-tags').data('tag');
            $tag_obj.add('Programmatically Added');

            var index = $tag_obj.inValues('some tag');
            $tag_obj.remove(index);
        }
        catch (e) {
            //display a textarea for old IE, because it doesn't support this plugin or another one I tried!
            tag_input.after('<textarea id="' + tag_input.attr('id') + '" name="' + tag_input.attr('name') + '" rows="3">' + tag_input.val() + '</textarea>').remove();
            //autosize($('#form-field-tags'));
        }


    });
</script>

</body>
</html>