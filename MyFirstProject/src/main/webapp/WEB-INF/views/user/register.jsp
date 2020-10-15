<%--
  Created by IntelliJ IDEA.
  User: 柒染
  Date: 2020/7/6
  Time: 18:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"><!--移动设备-->

    <title>注册</title>
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
        <style>
        body{
            background-image: url("${ctxStatics}/photo/registerBackgroundImg.jpg");
        }

        i{
            right: 10px!important;/*调整验证提示 错位*/
        }
        form label{
            font-size: 16px;
            font-weight: normal;
        }
        #captchaImg{
            width: 100%;
            height: 100%;
        }
        #borderRow{
            margin-top: 5%;
        }
        #choiceUl li :hover{/*标签页动态*/
            fill: red;
            color: red;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            validator();//前端验证
        })
        function  changeCaptcha() {//更换验证码
            $("#captchaImg").attr("src",'${path}/resource/captcha?'+Math.random());
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row" id="headRow"><!--页 头-->
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
                <div class="collapse navbar-collapse navbar-right" id="list">
                    <ul class="nav navbar-nav" id="choiceUl">
                        <li>
                            <a href="${path}/menu/mainPageRedirect">
                                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" t="1594126962699" class="icon" viewBox="0 0 1024 1024" version="1.1" p-id="6223" width="16" height="16"><defs><style type="text/css"/></defs><path d="M1007.072 542.48L512.016 79.152 16.96 541.12c-9.68 9.04-10.224 24.24-1.168 33.92 9.04 9.68 24.24 10.224 33.92 1.168l462.272-431.36L974.272 577.52a23.942 23.942 0 0 0 16.4 6.48c6.4 0 12.8-2.544 17.52-7.6 9.056-9.68 8.56-24.864-1.12-33.92zM856 552c-13.248 0-24 10.752-24 24v368H640V688H384v256H192V576c0-13.248-10.736-24-24-24s-24 10.752-24 24v416h288V736h160v256h288V576c0-13.248-10.752-24-24-24zM688 208h144v128c0 13.264 10.752 24 24 24s24-10.736 24-24V160H688c-13.248 0-24 10.736-24 24s10.752 24 24 24z" fill="" p-id="6224"/></svg>
                                首页
                            </a>
                        </li>
                        <li>
                            <a href="${path}/user/login">
                                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" t="1594124675859" class="icon" viewBox="0 0 1024 1024" version="1.1" p-id="2703" width="16" height="16"><defs><style type="text/css"/></defs><path d="M512.015052 90.794767c102.234426 0 210.655299 86.113538 210.655299 150.386359l0 150.446568c0 57.905747-24.083493 168.689813-88.672409 219.536087-16.467088 12.974982-25.016728 33.58142-22.54817 54.398589 2.468558 20.847273 15.609114 38.864736 34.665177 47.625107l261.998295 124.240717c2.032045 0.933235 25.107041 4.801646 25.107041 37.013318l0.030104 58.763722L90.794767 933.25039l0-61.09681c0-23.932971 17.972306-31.44401 25.137145-34.75549l264.391592-124.767544c18.950698-8.700162 32.031045-26.642364 34.574864-47.339115 2.558871-20.696751-5.79509-41.242981-22.0665-54.293224-62.54182-50.214082-91.682846-160.591739-91.682846-219.370513l0-150.446568C301.164075 178.293106 410.668705 90.794767 512.015052 90.794767M512.015052 30.586036c-132.940879 0-271.059709 110.94964-271.059709 210.59509l0 150.446568c0 65.732883 29.999 198.73397 114.200911 266.318271L90.74961 782.728561c0 0-60.163575 26.807938-60.163575 60.178627l0 90.343202c0 33.250272 26.943407 60.178627 60.163575 60.178627l842.500779 0c33.250272 0 60.178627-26.928355 60.178627-60.178627l0-90.343202c0-35.387682-60.178627-60.178627-60.178627-60.178627l-261.998295-124.240717c83.404145-65.642569 111.626988-194.59462 111.626988-266.86015l0-150.446568C782.879083 141.535675 644.940879 30.586036 512.015052 30.586036L512.015052 30.586036z" p-id="2704"/></svg>
                                登录
                            </a>
                        </li>
                        <li href="${path}/tourist/register" class="active">
                            <a href="#">
                                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" t="1594126856900" class="icon" viewBox="0 0 1024 1024" version="1.1" p-id="5148" width="16" height="16"><defs><style type="text/css"/></defs><path d="M493.411643 554.452881c-132.722814 0-240.718457-107.974154-240.718457-240.696967 0-132.701325 107.995643-240.675478 240.718457-240.675478s240.675478 107.974154 240.675478 240.675478C734.087121 446.478727 626.134457 554.452881 493.411643 554.452881zM493.411643 139.247414c-96.235808 0-174.551478 78.294181-174.551478 174.508499 0 96.235808 78.31567 174.529989 174.551478 174.529989s174.508499-78.294181 174.508499-174.529989C667.920142 217.541595 589.647451 139.247414 493.411643 139.247414z" p-id="5149"/><path d="M96.92347 950.919565c-18.264992 0-33.083489-14.818497-33.083489-33.083489 0-236.863662 192.686511-429.550173 429.571662-429.550173 104.808044 0 205.739804 38.188764 284.22739 107.521852 13.699 12.0832 14.991436 32.997532 2.929726 46.696532-12.147668 13.72049-33.040511 14.925944-46.696532 2.885723-66.425875-58.650795-151.806451-90.937129-240.460584-90.937129-200.398146 0-363.404684 163.006538-363.404684 363.383194C130.007983 936.101067 115.188462 950.919565 96.92347 950.919565z" p-id="5150"/><path d="M927.075507 837.861626 701.003632 837.861626c-18.264992 0-33.083489-14.818497-33.083489-33.083489s14.818497-33.083489 33.083489-33.083489l226.071875 0c18.264992 0 33.083489 14.818497 33.083489 33.083489S945.340499 837.861626 927.075507 837.861626z" p-id="5151"/><path d="M814.040081 950.898075c-18.264992 0-33.083489-14.818497-33.083489-33.083489L780.956591 691.741687c0-18.264992 14.818497-33.083489 33.083489-33.083489s33.083489 14.818497 33.083489 33.083489l0 226.071875C847.12357 936.079578 832.305073 950.898075 814.040081 950.898075z" p-id="5152"/></svg>                        注册
                            </a>
                        </li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div>
        </nav>
    </div>

    <div class="row" id="borderRow">
        <div class="col-md-4"></div>
        <form id="registerForm" class="form-horizontal col-md-5" action="${path}/tourist/checkRegister" method="post">
            <div class="form-group" >
                <h3 style="margin-left: 8%">注册新用户</h3>
                <hr>
                <%--异常提示--%>
                <div class="alert alert-danger alert-dismissible ${empty errorMessage ? 'hide' : ''}" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>错误!</strong>
                    <c:forEach var="error" items="${errorMessage}">
                        <c:out value="${error}"></c:out>.
                    </c:forEach>
                </div>
            </div>
            <div class="form-group" >
                <label for="mailbox" class="col-sm-3 control-label" style="padding-right: 0px">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label>
                <div class="col-sm-6">
                    <input type="text" name="mailbox" class="form-control" id="mailbox" placeholder="需要通过邮件激活账户">
                </div>
            </div>
            <div class="form-group">
                <label for="phoneNum" class="col-sm-3 control-label" style="padding-right: 0px">手&nbsp;机&nbsp;&nbsp;号&nbsp;码:</label>
                <div class="col-sm-6">
                    <input type="tel"  name="phoneNum" class="form-control" id="phoneNum" placeholder="手机号码">
                </div>
            </div>

            <div class="form-group">
                <label for="username" class="col-sm-3 control-label" style="padding-right: 0px">登录用户名:</label>
                <div class="col-sm-6">
                    <input type="text" name="username" class="form-control" id="username" placeholder="登录用户名">
                </div>
            </div>

            <div class="form-group">
                <label for="name" class="col-sm-3 control-label" style="padding-right: 0px">显&nbsp;示&nbsp;&nbsp;昵&nbsp;称:</label>
                <div class="col-sm-6">
                    <input type="text" name="name" class="form-control" id="name" placeholder="显示昵称">
                </div>
            </div>

            <div class="form-group">
                <label for="password" class="col-sm-3 control-label" style="padding-right: 0px;">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:</label>
                <div class="col-sm-6">
                    <input type="password" name="password" class="form-control" id="password" placeholder="密码">
                </div>
            </div>

            <div class="form-group">
                <label for="checkPassword" class="col-sm-3 control-label" style="padding-right: 0px">确&nbsp;认&nbsp;&nbsp;密&nbsp;码:</label>
                <div class="col-sm-6">
                    <input type="password" name="checkPassword" class="form-control" id="checkPassword" placeholder="密码确认">
                </div>
            </div>

            <div class="form-group" id="captchaDiv">
                <label for="captcha" class="col-sm-2 control-label col-sm-offset-1" style="padding-right: 0px">验证码:</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control" id="captcha" placeholder="验证码" name="captcha">
                    <!--<span class="glyphicon glyphicon-remove form-control-feedback " aria-hidden="true" id="captchaError" style="display: none"></span>-->
                </div>
                <div class="col-sm-4">
                    <img src="${path}/resource/captcha" id="captchaImg" onclick="changeCaptcha()">
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-6">
                    <button type="submit" name="ToSubmit" class="btn btn-primary btn-lg btn-block">注&nbsp;&nbsp;&nbsp;&nbsp;册</button>
                </div>
            </div>
            <div class="form-group" style="margin-top: 5%">
                <div class="col-sm-offset-3 col-sm-6">
                    <p class="text-center"><small>﹡点击 “注册” 按钮，即表示您同意并愿意遵守<a href="#"> 用户协议</a></small></p>
                </div>
            </div>
        </form>
    </div>
</div>
<input id="path" type="hidden" value="${path}" />
</body>
</html>