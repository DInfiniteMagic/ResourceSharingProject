<%--
  User: 柒染
  Date: 2020/7/14
  Time: 22:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>主界面</title>
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
<%--    <style>
        #myCarousel img{
              height: 500px;
               width: 100%;
              background-size: cover;
           }
    </style>
    <script>
        $(function () {
            $("#myCarousel").carousel('cycle');
        })
    </script>--%>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="head.jsp" flush="true"></jsp:include>
    <jsp:include page="webSocket.jsp" flush="true"></jsp:include>
</div>
    <%--<div class="row">
    <div class="col-md-8 col-md-offset-2" >
        <div id="myCarousel" class="carousel slide ">
            <!-- 轮播（Carousel）指标 -->
            <ol class="carousel-indicators">
                <li data-target="#myCarousel" data-slide-to="1" class="active"></li>
                <li data-target="#myCarousel" data-slide-to="3"></li>

            </ol>
            <!-- 轮播（Carousel）项目 -->
            <div class="carousel-inner">
                <div class="item active" >
                    <img src="${ctxStatics}/photo/Carousel/2.jpg">
                </div>
                <div class="item">
                    <img src="${ctxStatics}/photo/Carousel/3.jpg">
                </div>
            </div>
            <!-- 轮播（Carousel）导航 -->
            <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev" >
                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next" >
                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
    </div>
</div>--%>
</body>
</html>