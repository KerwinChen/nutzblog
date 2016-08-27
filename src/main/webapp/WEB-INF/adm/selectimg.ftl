<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <!-- Bootstrap core CSS -->
    <link href="/adm/assets/css/ace.min.css" rel="stylesheet">
    <link href="/adm/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="/adm/assets/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="/adm/assets/js/jquery-file-upload/jquery.fileupload.css" rel="stylesheet">

    <!--[if lt IE 9]>
    <script src="/adm/assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="/adm/assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="/adm/assets/js/html5shiv.min.js"></script>
    <script src="/adm/assets/js/respond.min.js"></script>
    <![endif]-->


    <script src="/adm/assets/js/jquery-1.11.3.min.js"></script>
    <script src="/adm/assets/js/jquery-file-upload/jquery.ui.widget.js"></script>
    <script src="/adm/assets/js/jquery-file-upload/jquery.iframe-transport.js"></script>
    <script src="/adm/assets/js/jquery-file-upload/jquery.fileupload.js"></script>

    <script src="/adm/assets/js/jquery.json.min.js"></script>
    <script src="/adm/assets/js/dialog/artDialog.js?skin=chrome"></script>
    <script src="/adm/assets/js/dialog/plugins/iframeTools.js"></script>


</head>
<body>
<div class="container-fuld">
    <div class="row">
        <div class="col-md-12" style="margin: 6px">
            <input style="float: left;width: 300px" class="form-control" type="input" name="filterkey"
                   id="filterkey" class="required">
            <button style="margin-left: 10px" id="btn_search" type="button" class="btn btn-default margintop10">搜索
            </button>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <ul id="list_tbody" class="ace-thumbnails">

            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12" style="margin: 0px 6px">
            <div>
                <ul id="pager" class="pagination">

                </ul>
            </div>
        </div>
    </div>
</div>

<script src="/adm/assets/js/base64/base64.js"></script>
<script src="/adm/assets/js/dialog/artDialog.js?skin=chrome"></script>
<script src="/adm/assets/js/dialog/plugins/iframeTools.js"></script>
<script src="/adm/assets/js/layer/layer.js"></script>
<script>

    $(function () {

        $("#btn_search").bind("click", function () {
            page(1, $("#filterkey").val());
        });
        page(1);

    });

    function page(pageno, args) {
        if (args) {
            args = encodeURIComponent(args);
        }
        else {
            args = "";
        }

        $.post("/adm/upload/doshowlist_selectimg/?args=" + args + "&pageno=" + pageno + "&t=" + new Date().getTime() + "", function (data) {

            console.log(data);
            var html = "";

            if (data["datas"] && data["datas"].length > 0) {

                for (var i = 0; i < data["datas"].length; i++) {
                    var v = data["datas"][i];
                    html = html + "<li>\n" +
                            "		<a href=\"javascript:void(0);\" data-rel=\"colorbox\">\n" +
                            "				<img alt=\"150x150\" width=\"150px\" height=\"150px\"\n" +
                            "						 src=\"" + v["_downurl"] + "\">\n" +
                            "				<div   filename='" + v["_name"] + "'  filekey='" + v["_filekey"] + "' downurl='" + v["_downurl"] + "'  class=\"text\">\n" +
                            "						<div class=\"inner\">" + v["_name"] + "</div>\n" +
                            "				</div>\n" +
                            "		</a>\n" +
                            "		<div class=\"tools tools-bottom\">\n" +
                            "				<a target='_blank' href=\"/view/" + v["_filekey"] + "/\">\n" +
                            "						<i class=\"ace-icon fa fa-link\"></i>\n" +
                            "				</a>\n" +
                            "		</div>\n" +
                            "</li>"
                }
            }

            $("#list_tbody").empty().html(html);
            $("#pager").html(data["pages"]);


            // 关闭当前页，返回到父页数据
            var win = art.dialog.top;
            var imgid = $(win.document.getElementById('imgid'));


            $("#list_tbody li .text").each(function (item, idx) {
                        $(this).bind("click", function () {
                            var filename = $(this).attr("filename");
                            var filekey = $(this).attr("filekey");
                            var downurl = $(this).attr("downurl");
                            var rsvalue = "  <a target=\"_blank\"  href=\"/view/" + filekey + "/\">查看 " + filename + "</a>";

                            layer.msg("选中 " + filename, {time: 1000, icon: 1}, function () {
                                var win = art.dialog.top;
                                $(imgid).empty();
                                $(imgid).attr("imgid", filekey);
                                $(imgid).append(rsvalue);
                                art.dialog.close();
                            });


                        });
                    }
            );


        })
    }
</script>
</body>
</html>