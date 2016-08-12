<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="utf-8">
    <title>NutzBlog 后台管理</title>
    <meta name="description" content="frequently asked questions using tabs and accordions">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <link rel="icon" href="/static/img/fav.ico" type="image/x-icon">
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="/adm/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="/adm/assets/font-awesome/4.5.0/css/font-awesome.min.css">

    <!-- page specific plugin styles -->
    <!-- text fonts -->
    <link rel="stylesheet" href="/adm/assets/css/fonts.googleapis.com.css">

    <!-- ace styles -->
    <link rel="stylesheet" href="/adm/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style">

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="/adm/assets/css/ace-part2.min.css" class="ace-main-stylesheet"/>
    <![endif]-->
    <link rel="stylesheet" href="/adm/assets/css/ace-skins.min.css">
    <link rel="stylesheet" href="/adm/assets/css/ace-rtl.min.css">

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="/adm/assets/css/ace-ie.min.css"/>
    <![endif]-->

    <!-- inline styles related to this page -->
    <!-- ace settings handler -->
    <script src="/adm/assets/js/ace-extra.min.js"></script>
    <style>@keyframes nodeInserted {
               from {
                   outline-color: #fff
               }
               to {
                   outline-color: #000
               }
           }

    @-moz-keyframes nodeInserted {
        from {
            outline-color: #fff
        }
        to {
            outline-color: #000
        }
    }

    @-webkit-keyframes nodeInserted {
        from {
            outline-color: #fff
        }
        to {
            outline-color: #000
        }
    }

    @-ms-keyframes nodeInserted {
        from {
            outline-color: #fff
        }
        to {
            outline-color: #000
        }
    }

    @-o-keyframes nodeInserted {
        from {
            outline-color: #fff
        }
        to {
            outline-color: #000
        }
    }

    .ace-save-state {
        animation-duration: 10ms;
        -o-animation-duration: 10ms;
        -ms-animation-duration: 10ms;
        -moz-animation-duration: 10ms;
        -webkit-animation-duration: 10ms;
        animation-delay: 0s;
        -o-animation-delay: 0s;
        -ms-animation-delay: 0s;
        -moz-animation-delay: 0s;
        -webkit-animation-delay: 0s;
        animation-name: nodeInserted;
        -o-animation-name: nodeInserted;
        -ms-animation-name: nodeInserted;
        -moz-animation-name: nodeInserted;
        -webkit-animation-name: nodeInserted
    }</style>
    <style>@keyframes nodeInserted {
               from {
                   outline-color: #fff
               }
               to {
                   outline-color: #000
               }
           }

    @-moz-keyframes nodeInserted {
        from {
            outline-color: #fff
        }
        to {
            outline-color: #000
        }
    }

    @-webkit-keyframes nodeInserted {
        from {
            outline-color: #fff
        }
        to {
            outline-color: #000
        }
    }

    @-ms-keyframes nodeInserted {
        from {
            outline-color: #fff
        }
        to {
            outline-color: #000
        }
    }

    @-o-keyframes nodeInserted {
        from {
            outline-color: #fff
        }
        to {
            outline-color: #000
        }
    }

    .ace-save-state {
        animation-duration: 10ms;
        -o-animation-duration: 10ms;
        -ms-animation-duration: 10ms;
        -moz-animation-duration: 10ms;
        -webkit-animation-duration: 10ms;
        animation-delay: 0s;
        -o-animation-delay: 0s;
        -ms-animation-delay: 0s;
        -moz-animation-delay: 0s;
        -webkit-animation-delay: 0s;
        animation-name: nodeInserted;
        -o-animation-name: nodeInserted;
        -ms-animation-name: nodeInserted;
        -moz-animation-name: nodeInserted;
        -webkit-animation-name: nodeInserted
    }</style>

    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->
    <!--[if lte IE 8]>
    <script src="/adm/assets/js/html5shiv.min.js"></script>
    <script src="/adm/assets/js/respond.min.js"></script>
    <![endif]-->

</head>

<body class="no-skin">
<div id="navbar" class="navbar navbar-default  ace-save-state navbar-fixed-top" style="">
    <div class="navbar-container ace-save-state" id="navbar-container">
        <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
            <span class="sr-only">Toggle sidebar</span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>
        </button>

        <div class="navbar-header pull-left">
            <a href="index.html" class="navbar-brand">
                <small>
                    <i class="fa fa-laptop"></i> NutzBlog
                </small>
            </a>
        </div>

        <div class="navbar-buttons navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">


                <li class="light-blue dropdown-modal">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle" aria-expanded="false">
                        <img class="nav-user-photo" src="assets/images/avatars/user.jpg" alt="Jason's Photo">
                        <i class="ace-icon fa fa-caret-down"></i>
                    </a>

                    <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <a href="#">
                                <i class="ace-icon fa fa-cog"></i>
                                Settings
                            </a>
                        </li>

                        <li>
                            <a href="profile.html">
                                <i class="ace-icon fa fa-user"></i>
                                Profile
                            </a>
                        </li>

                        <li class="divider"></li>
                        <li>
                            <a id="a_logout" href="#">
                                <i class="ace-icon fa fa-power-off"></i>
                                Logout
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    <!-- /.navbar-container -->
</div>

<div class="main-container ace-save-state" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.loadState('main-container')
        } catch (e) {
        }
    </script>

    <div id="sidebar" class="sidebar                  responsive                    ace-save-state sidebar-fixed"
         data-sidebar="true" data-sidebar-scroll="true" data-sidebar-hover="true">
        <script type="text/javascript">
            try {
                ace.settings.loadState('sidebar')
            } catch (e) {
            }
        </script>


        <!-- /.sidebar-shortcuts -->

        <div class="nav-wrap-up pos-rel">
            <div class="nav-wrap">
                <div style="position: relative; top: 0px; transition-property: top; transition-duration: 0.15s;">
                    <div class="nav-wrap-up pos-rel">
                        <div class="nav-wrap">
                            <ul class="nav nav-list"
                                style="position: relative; top: 0px; transition-property: top; transition-duration: 0.15s;">
                                <li class="highlight">
                                    <a href="#" class="dropdown-toggle">
                                        <i class="menu-icon fa fa-desktop"></i>
                                        <span class="menu-text">
                                             博客管理
                                        </span>
                                        <b class="arrow fa fa-angle-down"></b>
                                    </a>
                                    <b class="arrow"></b>
                                    <ul class="submenu">
                                        <li>
                                            <a href="/adm/listblog">
                                                <i class="menu-icon fa fa-caret-right"></i>
                                                博客列表
                                            </a>
                                            <b class="arrow"></b>
                                        </li>
                                        <li>
                                            <a href="/adm/wblog">
                                                <i class="menu-icon fa fa-caret-right"></i>
                                                写博客
                                            </a>
                                            <b class="arrow"></b>
                                        </li>
                                    </ul>
                                </li>

                                <li class="highlight">
                                    <a href="#" class="dropdown-toggle">
                                        <i class="menu-icon fa fa-desktop"></i>
                                        <span class="menu-text">
                                             系列教程管理
                                        </span>
                                        <b class="arrow fa fa-angle-down"></b>
                                    </a>
                                    <b class="arrow"></b>
                                    <ul class="submenu nav-show" style="display: none;">
                                        <li class="">
                                            <a href="/adm/listseris">
                                                <i class="menu-icon fa fa-caret-right"></i>
                                                教程列表
                                            </a>
                                            <b class="arrow"></b>
                                        </li>
                                        <li class="">
                                            <a href="/adm/wseris">
                                                <i class="menu-icon fa fa-caret-right"></i>
                                                写教程
                                            </a>
                                            <b class="arrow"></b>
                                        </li>
                                    </ul>
                                </li>

                                <li class="highlight">
                                    <a href="#" class="dropdown-toggle">
                                        <i class="menu-icon fa fa-desktop"></i>
                                        <span class="menu-text">
                                             读书笔记管理
                                        </span>
                                        <b class="arrow fa fa-angle-down"></b>
                                    </a>
                                    <b class="arrow"></b>
                                    <ul class="submenu nav-show" style="display: none;">
                                        <li class="">
                                            <a href="/adm/listnote">
                                                <i class="menu-icon fa fa-caret-right"></i>
                                                读书笔记列表
                                            </a>
                                            <b class="arrow"></b>
                                        </li>
                                        <li class="">
                                            <a href="/adm/wnote">
                                                <i class="menu-icon fa fa-caret-right"></i>
                                                写读书笔记
                                            </a>
                                            <b class="arrow"></b>
                                        </li>
                                    </ul>
                                </li>

                                <li class="highlight active">
                                    <a href="/adm/settings">
                                        <i class="menu-icon fa fa-tachometer"></i>
                                        <span class="menu-text"> 选项设置 </span>
                                    </a>
                                    <b class="arrow"></b>
                                </li>


                            </ul>
                        </div>
                        <div class="ace-scroll nav-scroll scroll-disabled">
                            <div class="scroll-track scroll-hover" style="display: none;">
                                <div class="scroll-bar"
                                     style="top: 0px; transition-property: top; transition-duration: 0.1s;"></div>
                            </div>
                            <div class="scroll-content">
                                <div></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="ace-scroll nav-scroll scroll-disabled">
                <div class="scroll-track" style="display: none; height: 569px;">
                    <div class="scroll-bar"
                         style="top: 0px; transition-property: top; transition-duration: 0.1s; height: 557px;"></div>
                </div>
                <div class="scroll-content">
                    <div></div>
                </div>
            </div>
        </div>
        <!-- /.nav-list -->

        <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse" style="z-index: 1;">
            <i id="sidebar-toggle-icon" class="ace-save-state ace-icon fa fa-angle-double-left"
               data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
        </div>
    </div>

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
                    <li class="active">选项设置</li>
                </ul>
            </div>
            <div class="page-content">
                <!-- /.ace-settings-container -->
                <div class="page-header">
                    <h1>
                        选项设置
                        <small>
                            <i class="ace-icon fa fa-angle-double-right"></i>
                        </small>
                    </h1>
                </div>
                <!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">
                        <div class="tabbable">
                            选项设置
                        </div>
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.page-content -->
        </div>
    </div>
    <!-- /.main-content -->

    <div class="footer">
        <div class="footer-inner">
            <div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">Ace</span>
							Application © 2013-2014
						</span>

                &nbsp; &nbsp;
						<span class="action-buttons">
							<a href="#">
                                <i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
                            </a>

							<a href="#">
                                <i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
                            </a>

							<a href="#">
                                <i class="ace-icon fa fa-rss-square orange bigger-150"></i>
                            </a>
						</span>
            </div>
        </div>
    </div>

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
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

<!-- page specific plugin scripts -->

<!-- ace scripts -->
<script src="/adm/assets/js/ace-elements.min.js"></script>
<script src="/adm/assets/js/ace.min.js"></script>
<!-- inline scripts related to this page -->
<script src="/adm/assets/js/common.js"></script>
<script aria-hidden="true" type="application/x-lastpass" id="hiddenlpsubmitdiv" style="display: none;"></script>

</body>
</html>