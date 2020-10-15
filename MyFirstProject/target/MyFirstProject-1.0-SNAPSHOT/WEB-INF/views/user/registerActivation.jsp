<%--
  Created by IntelliJ IDEA.
  User: 柒染
  Date: 2020/7/8
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>账户激活</title>
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
    <%--bootbox--%>
    <script src="${ctxStatics}/bootbox/bootbox.all.min.js"></script>
    <style>
        body{
            background-color: rgb(224,228,232);
        }
    </style>
    <script>
        $(function () {
            checkActivationCode();//激活码校验
            checkMail('#modifyMailForm');//数据校验
            $("#mailbox").blur(function () {
                if($("#modifyMailForm").data('bootstrapValidator').isValid()){
                    $("#modifyMailBtn").removeAttr("disabled");//按钮解锁
                }else{
                    $("#modifyMailBtn").attr({"disabled":"disabled"});//按钮加锁
                }
            });
            $("#modifyMailBtn").click(function () {
                $('#modifyMailbox').modal('hide');//关闭模态框
                $.ajax({
                    type: "POST",
                    url:"${path}/tourist/changeMailbox",
                    data:$("#mailbox").val(),
                    contentType:"text/xml",//发送数据的类型
                    success:function (data) {

                       if(data!=""){
                           bootbox.alert(data.toString());//输出信息
                       };

                    }
                });//请求修改邮箱
                $("#showMailbox").html($("#mailbox").val());
            })
        })
        function  sendMail(obj,second) {
            if(sendMail){
                countDown(obj,second);
                $.ajax({
                    type: "POST",
                    url:"${path}/tourist/sendMailAgain",
                    success:function (data) {
                       if(data!=""){
                           bootbox.alert(data.toString());//输出错误信息
                       }
                    }
                });//请求重新发送激活码
            }else{
                alert("其他接口")
            }
        }
        function countDown(obj,second) {// 获取默认按钮上的文字
            // 如果秒数还是大于0，则表示倒计时还没结束
            if (second >= 0) {
                if (typeof buttonDefaultValue === 'undefined') {
                    buttonDefaultValue =  obj.defaultValue;
                }
                //设置按钮为不可点击状态
                obj.disabled = true;
                // 按钮里的内容呈现倒计时状态
                obj.value = buttonDefaultValue + '(' + second + ')';
                // 时间减一
                second--;
                // 一秒后重复执行
                setTimeout(function () {
                    countDown(obj, second);
                }, 1000);
                // 否则，按钮重置为初始状态

            } else {
                // 按钮置未可点击状态
                obj.disabled = false;
                // 按钮里的内容恢复初始状态
                obj.value = buttonDefaultValue;
            }
        }

    </script>
</head>
<body>

<div class="container-fluid">
    <div class="row" style="margin-top: 13%">
        <div class="col-md-5"></div>
        <form action="${path}/tourist/checkActivationCode" method="post" id="activationCodeForm" class="form-horizontal col-md-3" style="background-color: white;border-radius: 5px;padding: 25px">
            <div class="form-group">
                <div class="col-sm-12">
                    <h3 style="text-align: center">${fns:getConfig('productName')}</h3>
                    <h4 style="text-align: center">账户激活</h4>
                    <h6 class="small" style="text-align: center" >已将验证码发送至:<strong id="showMailbox">${register.mailbox}</strong> <button type="button" class="btn btn-link btn-xs"  data-toggle="modal" data-target="#modifyMailbox">(修改)</button></h6>
                </div>
            </div>
            <!--异常信息-->
            <div class="alert alert-danger alert-dismissible ${empty errorMessage ? 'hide' : ''}" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <strong>错误!</strong><p class="text-left">${errorMessage}</p>
            </div>
            <div class="form-group" >
                <div class="col-sm-7" >
                    <input oninput = "value=value.replace(/[^\d]/g,'')" name="activationCode"  class="form-control" id="activationCode" placeholder="请输入6位邮箱验证码" maxlength="6">
                </div><%--限定只能输入6位数字--%>
                <div class="col-sm-5">
                    <input class="btn btn-default btn-block" onclick="sendMail(this,60)" value="重新发送">
                </div>
            </div>
            <div class="form-group" >
                <div class="col-sm-12">
                    <input type="submit" name="nextStep" class="btn btn-primary btn-lg btn-block" id="nextStep" value="下一步">
                </div>
            </div>
            <div class="form-group">
                <h6 style="text-align: center">若长时间未接收到验证码,请检查垃圾邮件</h6>
                <h6 style="text-align: center">如遇到困难请联系客服:<button type="button" class="btn btn-link btn-xs">客服</button></h6>
            </div>
        </form>
    </div>
</div>
<!-- Modal  弹出修改邮箱的模态框-->
<div class="modal fade" id="modifyMailbox" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="margin-top:10%">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">邮箱修改</h4>
            </div>
            <div class="modal-body" >
                <form id="modifyMailForm" class="form-horizontal" >
                    <div class="form-group" >
                        <label for="mailbox" class="col-sm-2 control-label" style="padding-right: 0px">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label>
                        <div class="col-sm-6">
                            <input type="text" name="mailbox" class="form-control" id="mailbox" placeholder="需要通过邮件激活账户">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="modifyMailBtn" disabled="true">修改</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>