<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>Login - NutzBlog</title>

    <meta name="description" content="User login page"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>

    <link rel="icon" href="/static/img/fav.ico" type="image/x-icon"/>
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="/adm/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/adm/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>

    <!-- text fonts -->
    <link rel="stylesheet" href="/adm/assets/css/fonts.googleapis.com.css"/>
    <!-- ace styles -->
    <link rel="stylesheet" href="/adm/assets/css/ace.min.css"/>
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="/adm/assets/css/ace-part2.min.css"/>
    <![endif]-->
    <link rel="stylesheet" href="/adm/assets/css/ace-rtl.min.css"/>

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="/adm/assets/css/ace-ie.min.css"/>
    <![endif]-->
    <link rel="stylesheet" href="/adm/assets/css/common.css"/>

    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->
    <!--[if lte IE 8]>
    <script src="/adm/assets/js/html5shiv.min.js"></script>
    <script src="/adm/assets/js/respond.min.js"></script>
    <![endif]-->
</head>

<body class="login-layout light-login">
<div class="main-container">
    <div class="main-content">
        <div class="row">
            <div class="col-sm-10 col-sm-offset-1">
                <div class="login-container" style="margin-top: 10%">

                    <div id="parent_msg_error" class="alert alert-danger hidden">
                        <button type="button" class="close" data-dismiss="alert">
                            <i class="ace-icon fa fa-times"></i>
                        </button>
                        <i class="ace-icon fa fa-times"></i>&nbsp;
                        <strong id="msg_error"></strong>
                        <br>
                    </div>

                    <div class="center">
                        <h1>
                            <i class="ace-icon fa  fa-laptop green"></i>
                            <span class="red">Nutz</span>
                            <span class="grey" id="id-text2">Blog</span>
                        </h1>
                        <h4 class="blue" id="id-company-text">&copy; javablog.net</h4>
                    </div>
                    <div class="space-6"></div>
                    <div class="position-relative">
                        <div id="login-box" class="login-box visible widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header blue lighter bigger">
                                        <i class="ace-icon fa fa-coffee green"></i>
                                        Please Enter Your Information
                                    </h4>

                                    <div class="space-6"></div>
                                    <form>
                                        <fieldset>
                                            <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input id="txt_username" type="text" class="form-control"
                                                                   placeholder="Username"/>
															<i class="ace-icon fa fa-user"></i>
														</span>
                                            </label>
                                            <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input id="txt_password" type="password"
                                                                   class="form-control"
                                                                   placeholder="Password"/>
															<i class="ace-icon fa fa-lock"></i>
														</span>
                                            </label>


                                        </fieldset>
                                    </form>

                                    <div class="social-or-login center">
                                        <span class="bigger-110"></span>
                                    </div>

                                    <div class="space-6"></div>
                                    <div class="social-login center">
                                        <button id="btn_login" type="button"
                                                class="width-35 center btn btn-sm btn-primary">
                                            <i class="ace-icon fa fa-key"></i>
                                            <span class="bigger-110">Login</span>
                                        </button>
                                    </div>
                                    <div class="space-6"></div>

                                </div>
                                <!-- /.widget-main -->
                            </div>
                            <!-- /.widget-body -->
                        </div>
                        <!-- /.login-box -->
                    </div>
                    <!-- /.position-relative -->
                </div>
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->
    </div>
    <!-- /.main-content -->
</div>
<!-- /.main-container -->

<!-- basic scripts -->

<!--[if !IE]> -->
<script src="/adm/assets/js/jquery-2.1.4.min.js"></script>
<!-- <![endif]-->

<!--[if IE]>
<script src="/adm/assets/js/jquery-1.11.3.min.js"></script>
<![endif]-->
<script type="text/javascript">
    if ('ontouchstart' in document.documentElement) document.write("<script src='/adm/assets/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>
<script src="/adm/assets/js/bootstrap.min.js"></script>

<!-- inline scripts related to this page -->
<script type="text/javascript">
    $(function () {

        $("#btn_login").bind("click", function () {
            $("#parent_msg_error").addClass("hidden");

            var username = $("#txt_username").val();
            var password = $("#txt_password").val();
            if (username && password) {

                $.post("/adm/dologin", {username: username, password: password}, function (rs) {

                    if (rs["status"] == "error") {
                        $("#parent_msg_error").removeClass("hidden");
                        $("#msg_error").text(rs["desc"]);
                    }
                    else {
                        window.location.href = "/adm/index";
                    }

                });

            }

        })

    });


</script>
</body>
</html>
