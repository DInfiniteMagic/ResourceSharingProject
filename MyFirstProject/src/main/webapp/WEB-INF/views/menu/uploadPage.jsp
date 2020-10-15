<%--
   Created by IntelliJ IDEA.
   User: 柒染
   Date: 2020/7/14
   Time: 22:05
   To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>文件上传</title>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <!--BootstrapValidator -->
    <script src="${ctxStatics}/bootstrap/js/bootstrapValidator.min.js"></script>
    <script src="${ctxStatics}/bootstrap/js/zh_CN.js"></script><!--国际化-->
    <link href="${ctxStatics}/bootstrap/css/bootstrapValidator.min.css" rel="stylesheet" />
    <script src="${ctxStatics}/bootstrap/js/MyValidator.js"></script>
    <script src="${ctxStatics}/bootbox/bootbox.all.min.js"></script>
    <!--粒子背景特效-->
    <script src="${ctxStatics}/backgroundDynamic/js/particle.js"></script>
   <link rel="stylesheet" href="${ctxStatics}/backgroundDynamic/css/particle.css">
    <!--引入加载的css-->
    <link rel="stylesheet" href="${ctxStatics}/load/upload/css/uploadContext.css">

    <%--jqgrid--%>
    <!--jqgrid 核心-->
    <script src="${ctxStatics}/jqgrid/js/jquery.jqGrid.min.js"></script>
    <!--jqgrid 国际化-->
    <script src="${ctxStatics}/jqgrid/js/grid.locale-cn.js"></script>
    <!--jqgrid 适配bootstrap样式 css-->
    <link rel="stylesheet" href="${ctxStatics}/jqgrid/css/ui.jqgrid-bootstrap.css" />
    <!--jqgrid 核心 css-->
    <link rel="stylesheet" href="${ctxStatics}/jqgrid/css/ui.jqgrid.css" />

    <link rel="stylesheet" href="${ctxStatics}/load/upload/css/uploadUnreviewed.css">

    <style>
        #fileDiv input {
            position: absolute;
            font-size: 100%;
            width: 100%;
            height: 100%;
            right: 0;
            top: 0;
            opacity:0;
        }
        #uploadSpan svg{
            margin-top: 4px;
        }

        .choiceli a{
            color: black;
            cursor:pointer;
        }
        .activeChoice a{
            background-color: rgb(217,83,79);
        }
        .choiceli a:hover{
            color: white;
            background-color: rgb(217,83,79)!important;
        }
        #resourceDescriptionDiv small{/*调整textarea标签下 bootstrap格式不正确的情况*/
            display: block;
            width: 100%;
        }
    </style>
    <script>
        var isLogin=false;
        function activeChange(id){
            $(".activeChoice").removeClass("activeChoice");
            $(id).addClass("activeChoice");
        }
        $(function() {
            $.ajax({
                type:"POST",
                url:"${path}/user/isLogin",
                dataType:"json",
                success:function(data) {
                    if(data.name==null){//未登录
                       isLogin=false;
                    }else{
                        isLogin=true;
                    }
                }
            });//页面加载时去后台判断该账户是否登录
            //$.getScript("${ctxStatics}/load/upload/js/uploadContext.js");
            $("#contextDiv").load("${ctxStatics}/load/upload/uploadContext.jsp");//开始默认选择文件上传选项
            $("#resourceLoadA").click(function () {
                activeChange("#resourceLoadli");
                 $("#contextDiv").load("${ctxStatics}/load/upload/uploadContext.jsp");
                //$.getScript("${ctxStatics}/load/upload/js/uploadContext.js");
             });

            $("#resourcePendingReviewA").click(function () {
               if(isLogin==true){
                    activeChange("#resourcePendingReviewli");
                    $("#contextDiv").load("${ctxStatics}/load/upload/uploadUnreviewed.jsp");
                }else{
                    bootbox.alert("请登录！");
                }
            });

            $("#resourceAuditedA").click(function () {
               if(isLogin==true){
                    activeChange("#resourceAuditedli");
                    $("#contextDiv").load("${ctxStatics}/load/upload/uploadAudited.jsp");
                }else{
                    bootbox.alert("请登录！");
                }
            });

            $("#resourceReturnA").click(function () {
               if(isLogin==true){
                    activeChange("#resourceReturnli");
                    $("#contextDiv").load("${ctxStatics}/load/upload/uploadReturn.jsp");
                }else{
                    bootbox.alert("请登录！");
                }
            });
        })
    </script>
</head>
<body>
<canvas id="canvas" ></canvas><!--背景特效-->
<div class="container-fluid" id="resourceUploadSign">
    <jsp:include page="head.jsp" flush="true"></jsp:include>
    <jsp:include page="webSocket.jsp" flush="true"></jsp:include>
    <div class="col-md-2"></div>
    <div class="col-md-8" style="background-color: white;margin-top: 50px">
        <div id="choiceDiv" class="row">
            <div class="col-sm-8 col-sm-offset-2" style="margin-top: 10px">
                <ul class="nav nav-pills nav-justified" id="choiceUi">
                    <li role="presentation" class="choiceli activeChoice" id="resourceLoadli"><a  id="resourceLoadA">资源上传</a></li>
                    <li role="presentation" class="choiceli" id="resourcePendingReviewli"><a  id="resourcePendingReviewA">待审核</a></li>
                    <li role="presentation" class="choiceli" id="resourceAuditedli"><a id="resourceAuditedA">已审核</a></li>
                    <li role="presentation" class="choiceli" id="resourceReturnli"><a id="resourceReturnA">退回</a></li>
                </ul>
            </div>
        </div>
        <div id="contextDiv" style="margin-top: 20px">

        </div>
    </div>
    </div>
    <input value="${path}" id="path" hidden="hidden">
</body>
</html>