<html lang="en">
<#include "../common/header.ftl">
<#--tools-->
<script src="/adm/assets/js/jquery.json.min.js"></script>
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

<link rel="stylesheet" href="/adm/assets/js/editor.md/css/editormd.css"/>
<script src="/adm/assets/js/editor.md/editormd.min.js"></script>

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
                                    <input type="text" name="_tags" id="_tags"
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
                                    <div class="showimg" id="imgid" imgid="${obj.item._toppic!""}"
                                         style="margin: 2px auto">
                                        <a target="_blank"
                                           href="/view/${obj.item._toppic!""}/">${obj.item._toppic!""}</a>
                                    </div>
                                </div>
                            </div>

                        <#--内容区域-->
                            <div class="form-group">
                                <div id="test-editormd" style="z-index: 1800">
                                    <textarea style="display:none;">${obj.item._content_markdown!""}</textarea>
                                </div>
                            </div>

                            <div class="form-actions form-group clearfix">
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
<#include "../common/footer.ftl">
</div>

<#include  "../common/endjs.ftl">


<script>


    function tag_init() {
        var tag_input = $('#_tags');
        try {
            var tags = new Array();

        <#if (obj._tags) ??>
            <#list  obj._tags as one>
                tags.push("${one._name}");
            </#list>
        </#if>

            tag_input.tag(
                    {
                        placeholder: tag_input.attr('placeholder'),
                        source: tags
                    }
            )
        }
        catch (e) {
            tag_input.after('<textarea id="' + tag_input.attr('id') + '" name="' + tag_input.attr('name') + '" rows="3">' + tag_input.val() + '</textarea>').remove();
        }
    }


    $(function () {


        $("#btn_submit").bind("click", function () {
            var _title = $("#_title").val();
            var _showintro = $("#_showintro").val();
            var _content_html = testEditor.getHTML();
            var _content_markdown = testEditor.getMarkdown();
            var _tags = $("#_tags").val();


            if (!_title || !_showintro || !_content_html) {
                alert("内容不完整");
                return;
            }

            if (_content_html.indexOf("<h2") < 0) {
                alert("至少要有1个h2标签");
                return;
            }


            var _toppic = $("#imgid").attr("imgid");
            if (!_toppic) {
                alert("还没有上传图片");
                return;
            }


            var data = {};
            data._title = _title;
            data._showintro = _showintro;
            data._content_html = _content_html;
            data._content_markdown = _content_markdown;
            data._toppic = _toppic;
            data._id = $("#_id").val();
            data._tags = _tags;
            data._titleinlogo = $("#_titleinlogo").val();


            var jsondata = $.toJSON(data);

            $.post("/adm/single_mgr/doaddup", data, function (rs) {
                if (rs["status"] == "ok") {
                    window.location.href = "/adm/listblog";
                }
                console.log("返回结果" + $.toJSON(rs));
            });
        });
        tag_init();

        //设置哪个 btn_upload 为上传控件
        fileupload("fileupload_group");


        //弹出选择图片 iframe
        $('#btn_selectimg').bind('click', function () {
//            art.dialog.open('/adm/upload/selectimg',
//                    {
//                        id: "win-selectpage",
//                        title: '选择图片',
//                        width: 870,
//                        height: 620,
//                        left: '50%',
//                        top: '5%',
//                        fixed: false,
//                        drag: true,
//                        resize: true
//                    });
                    layer.open({
                        title: "选择图片",
                        type: 2,
//                        area: ['870px', '100px'],
                        area: ['900px', '90%'],
                        offset: ['5%', ''],
                        content: ['/adm/upload/selectimg', 'no']
                    });

                }
        );


        //编辑器
        var testEditor;
        testEditor = editormd("test-editormd", {
            width: "100%",
            height: 300,
            toolbarAutoFixed: false,
//            autoHeight: true,
//            watch: false,             //实时预览
            syncScrolling: "single",
            saveHTMLToTextarea: true,//getHTML() 的使用需要设置该属性为true
            toolbarIcons: function () {
                // Or return editormd.toolbarModes[name]; // full, simple, mini
                // Using "||" set icons align right.
//                return ["undo", "redo", "|", "bold", "hr", "|", "preview", "watch", "|", "fullscreen", "info", "testIcon", "testIcon2", "file", "faicon", "||", "watch", "fullscreen", "preview", "testIcon"]
                return [
                    "undo", "redo", "|",
                    "bold", "del", "italic", "quote", "uppercase", "lowercase", "|",
                    "h1", "h2", "h3", "h4", "h5", "h6", "|",
                    "list-ul", "list-ol", "hr", "|",
                    "watch", "preview", "fullscreen", "|",
                    "link", "reference-link", "image", "code", "preformatted-text", "code-block", "table", "datetime", "emoji", "html-entities",
                    "help", "info"
                ];
            },
            path: "/adm/assets/js/editor.md/lib/",
            imageUpload: true,
            imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL: "/adm/upload_editor",
            htmlDecode: true

        });


    });


</script>

</body>
</html>