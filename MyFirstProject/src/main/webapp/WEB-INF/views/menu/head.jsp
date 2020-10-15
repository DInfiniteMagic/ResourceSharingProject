<%--
  Created by IntelliJ IDEA.
  User: 柒染
  Date: 2020/7/15
  Time: 0:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>head</title>
        <style>
        #list #choiceUl li :hover{
            fill: red;
            color: red;
        }
        #showUsername{
            color: black;
            display: block;
            white-space:nowrap;
            overflow:hidden;
            text-overflow:ellipsis;
            height: 100%;
            width: 80px;
            line-height: 42px;
            margin-left:5px;
            float: left;
        }
    </style>
    <script>
        $(function() {
            $.ajax({
                type:"POST",
                url:"${path}/user/isLogin",
                dataType:"json",
                success:function(data) {
                    if(data.name==null){//未登录
                        $("#loginLi").removeClass("hide");
                        $("#statusLi").addClass("hide");
                    }else{
                        $("#loginLi").addClass("hide");
                        $("#statusLi").removeClass("hide");
                        $("#showUsername").html(data.name);
                       if(data.avatarUrl!=null){//当有头像的时候
                            $("#avatarImg").attr("src","../"+data.avatarUrl);
                       }
                    }
                }
            });//页面加载时去后台判断该账户是否登录
            if($("#resourceUploadSign").length>0){
                $("li").removeClass("active");
                $("#upload").attr("class", "active");
            }
            else if($("#resourcesList").length>0){
                 $("li").removeClass("active");
                $("#download").attr("class", "active");
            }
        });
    </script>
</head>
<body>
<div class="row" id="headRow">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#list" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${path}/menu/mainPageRedirect">
                    <img alt="Brand" class="img-rounded" style="margin-top: -10px" src="${ctxStatics}/photo/Brand6.png">
                </a>
            </div>

            <div class="collapse navbar-collapse" id="list">
                <ul class="nav navbar-nav  navbar-left" >
                    <li>
                        <a href="${path}/resources/downloadPageRedirect" id="download">
                            资源下载
                        </a>
                    </li>
                    <li id="upload">
                        <a href="${path}/resources/uploadPageRedirect">
                            资源上传
                        </a>
                    </li>
<%--                    <li>
                        <a href="#">
                            资源求助
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            商城
                        </a>
                    </li>--%>
                    <!--此处需要权限控制-->
                    <shiro:hasRole name="admin">
                        <li >
                            <a href="${path}/SystemManagement/SystemMonitoringRedirect">
                                后台管理
                            </a>
                        </li>
                    </shiro:hasRole>
                </ul>
                <ul class="nav navbar-nav  navbar-right" id="choiceUl">
                    <li>
                        <a href="#" style="padding: 7px 8px">
                            <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" t="1594540734521" class="icon" viewBox="0 0 1024 1024" version="1.1" p-id="2727" width="32" height="32"><defs><style type="text/css"/></defs><path d="M585.2 165.9c-7.6 0-13.8-6.2-13.8-13.8 0-32.7-26.6-59.2-59.2-59.2S453 119.4 453 152.1c0 7.6-6.2 13.8-13.8 13.8s-13.8-6.2-13.8-13.8c0-47.9 38.9-86.8 86.8-86.8s86.8 38.9 86.8 86.8c0 7.6-6.2 13.8-13.8 13.8zM512.2 959.3c-64.6 0-117.1-52.5-117.1-117.1 0-7.6 6.2-13.8 13.8-13.8s13.8 6.2 13.8 13.8c0 49.3 40.1 89.5 89.5 89.5s89.5-40.1 89.5-89.5c0-7.6 6.2-13.8 13.8-13.8s13.8 6.2 13.8 13.8c-0.1 64.6-52.6 117.1-117.1 117.1z" p-id="2728"/><path d="M833.6 857.2H190.7c-27 0-49-22-49-49 0-2.4 0.7-4.9 1.9-7l86-146.9V418.2c0-155.8 126.7-282.5 282.5-282.5s282.5 126.7 282.5 282.5v236.2l86 146.9c1.2 2.1 1.9 4.5 1.9 7 0 26.9-21.9 48.9-48.9 48.9z m-663.9-45.6c1.6 10.2 10.4 18 21.1 18h642.8c10.6 0 19.5-7.8 21.1-18L769 665.1c-1.2-2.1-1.9-4.5-1.9-7V418.2c0-140.5-114.3-254.9-254.9-254.9S257.3 277.6 257.3 418.2v239.9c0 2.4-0.7 4.9-1.9 7l-85.7 146.5z" p-id="2729"/></svg>
                            <div style="position:absolute;width: 20px;height: 15px;border-radius: 20px;margin:-34px 0px 0px 18px; background-color: red"><div style="color: white;font-size: 10px;text-align: center;line-height:15px;">99</div></div>
                        </a>
                    </li>
                    <li class="hide" id="loginLi">
                        <a href="${path}/user/login">
                            <!--用户登录信息-->
                            登录/注册
                        </a>
                    </li>
                    <li id="statusLi" class="dropdown hide">
                        <a id="drop3" href="#" style="padding:5px 5px 10px 10px!important;height: 45px" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                            <img id="avatarImg"   src="${ctxStatics}/photo/head.jpg" style="width: 40px;height: 40px;display: block;float: left" alt="暂无" class="img-circle">
                            <span  id="showUsername">用户名</span>
                            <span  style="display: block;float: right;margin-top: 20px" class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="drop3" style="z-index:999!important;">
                            <li><a href="#">个人中心</a></li>
                            <li><a href="#">我的收藏</a></li>
                            <li><a href="#">我的下载</a></li>
                            <li><a href="#">我的上传</a></li>
                            <li role="separator" class="divider"></li><!--分割线-->
                            <li><a href="${path}/user/logout">退出登录</a></li>
                        </ul>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div>
    </nav>
</div>
</body>
</html>
