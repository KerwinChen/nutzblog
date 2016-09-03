<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>
        生成HTML
    </title>
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">

    <!--[if lt IE 9]>
    <script src="/static/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="/static/js/ie-emulation-modes-warning.js"></script>
    <!--[if lt IE 9]>
    <script src="/static/js/html5shiv.min.js"></script>
    <script src="/static/js/respond.min.js"></script>
    <![endif]-->
    <script>
        var _hmt = _hmt || [];
    </script>
</head>
<body style="padding-top: 20px">


<div class="container">
    <div class="row">
        <div id="process_txt" class="alert alert-warning" role="alert">系统日志</div>
    </div>

    <div class="row">

        <div class="jumbotron">

        </div>
    </div>


</div>

<script src="/static/js/jquery.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/ie10-viewport-bug-workaround.js"></script>

<script>
    var timer = window.setInterval("ref()", 1000);
    function ref() {
        $.get("/getlog", function (data) {
            //登录失效
            if (data.indexOf("Login - NutzBlog")>0) {
                window.location.href = "/adm/login";
            }

            $(".jumbotron").html(data);
            sc();
        });
    }

    function sc() {
        var e = document.getElementById("end")
        e.scrollTop = e.scrollHeight;
    }
</script>


<div id="end" style="border:1px #ff9966 dashed;width:400;height:100;overflow-x:hidden;overflow-y:scroll">
</div>

</body>
</html>
