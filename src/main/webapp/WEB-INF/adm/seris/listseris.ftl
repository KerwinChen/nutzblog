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

<script id="template_list" type="text/html">
    {{each datas as value i}}
    <tr>
        <td>{{value._id}}</td>
        <td>{{value._title}}</td>
        <td>{{value.ut}}</td>
        <td>
            <a href="javascript:del({{value._id}});">删除</a>
            <a target="_self" href="/adm/wblog/?_id={{value._id}}">编辑</a>
            <a target="_blank" href="/page/{{value._id}}/{{value._titleen}}.html">预览</a>
            <a target="_blank" href="/html_single/{{value._id}}">[生成html]</a>
        </td>
    </tr>
    {{/each}}
</script>


<script>
    function del(id) {
        if (confirm("确定删除吗?")) {
            $.get("/adm/seris_mgr/del/?id=" + id + "", function (rs) {
                if (rs == "ok") {
                    window.location.reload();
                } else {
                    alert(rs);
                }
            });
        }
    }

    $(function () {
        page(1);
        $("#btn_q").bind("click", function () {
            page(1);
        });
    });

    function page(pageno) {
        var txt_q = $("#txt_q").val();
        $.post("/adm/seris_mgr/doshowlist/?isdraft=0&pageno=" + pageno + "&t=" + new Date().getTime() + "", {"txt_q": txt_q}, function (data) {
            console.log(data);
            var html = template('template_list', data);
            $("#list_tbody").html(html);
            $("#pager").html(data["pages"]);
        })
    }

</script>

</body>
</html>