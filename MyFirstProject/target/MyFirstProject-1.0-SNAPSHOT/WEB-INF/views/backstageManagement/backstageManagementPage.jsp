<%--
  Created by IntelliJ IDEA.
  User: 柒染
  Date: 2020/7/26
  Time: 19:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>后台管理</title>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
     <!--Charts js-->
    <script src="${ctxStatics}/Charts/js/Chart.min.js"></script>
    <!--Charts css-->
    <link rel="stylesheet" href="${ctxStatics}/Charts/css/Chart.min.css">
    <%--bootbox--%>
    <script src="${ctxStatics}/bootbox/bootbox.all.min.js"></script>
        <%--jqgrid--%>
    <!--jqgrid 核心-->
    <script src="${ctxStatics}/jqgrid/js/jquery.jqGrid.min.js"></script>
    <!--jqgrid 国际化-->
    <script src="${ctxStatics}/jqgrid/js/grid.locale-cn.js"></script>
    <!--jqgrid 适配bootstrap样式 css-->
    <link rel="stylesheet" href="${ctxStatics}/jqgrid/css/ui.jqgrid-bootstrap.css" />
    <!--jqgrid 核心 css-->
    <link rel="stylesheet" href="${ctxStatics}/jqgrid/css/ui.jqgrid.css" />

     <!--系统监控css-->
    <link rel="stylesheet" href="${ctxStatics}/load/BackstageManagement/css/systemMonitoring.css">
     <!--访问记录css-->
    <link rel="stylesheet" href="${ctxStatics}/load/BackstageManagement/css/accessRecords.css">
     <!--用户列表css-->
     <link rel="stylesheet" href="${ctxStatics}/load/BackstageManagement/css/userListTable.css"/>
    <script>

        function loadSystemMonitoring() {
            $("#contextPage").load("${ctxStatics}/load/BackstageManagement/SystemMonitoring.jsp");
        }
        function loadAccessRecords(){
             $("#contextPage").load("${ctxStatics}/load/BackstageManagement/accessRecords.jsp");
             clearInterval(autoProgressBar);/*暂停系统监控中获取系统信息的函数*/
             clearInterval(autoLine);
        }
        function loadAccountListTable(){
             $("#contextPage").load("${ctxStatics}/load/BackstageManagement/accountListTable.jsp");
             clearInterval(autoProgressBar);
             clearInterval(autoLine);
        }
        function loadOnlineUserListTable(){
            $("#contextPage").load("${ctxStatics}/load/BackstageManagement/onlineUserListTable.jsp");
             clearInterval(autoProgressBar);
             clearInterval(autoLine);
        }
        function allResourcesListTable(){
            $("#contextPage").load("${ctxStatics}/load/BackstageManagement/allResourcesListTable.jsp");
             clearInterval(autoProgressBar);
             clearInterval(autoLine);
        }
        function resourcesReviewListTable(){
            $("#contextPage").load("${ctxStatics}/load/BackstageManagement/resourcesReviewList.jsp");
             clearInterval(autoProgressBar);
             clearInterval(autoLine);
        }
         function resourcesReturnListTable(){
            $("#contextPage").load("${ctxStatics}/load/BackstageManagement/resourcesReturnListTable.jsp");
             clearInterval(autoProgressBar);
             clearInterval(autoLine);
        }
        $(function () {
            loadSystemMonitoring();
        })
    </script>
    <style>
        body{
            background-color: #3C3F41;
        }
        #pData{
            height:20px!important
        }
        #nData{
            height:20px!important
        }
        #Data{
            height: 30px;
            padding: 4px 4px 3px 3px;
        }
        .fm-button-icon-left {
            padding-left:7px;
        }
    </style>
<body>
    <jsp:include page="../menu/head.jsp" flush="true"></jsp:include>
    <jsp:include page="../menu/webSocket.jsp" flush="true"></jsp:include>
    <!--主体-->
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2" style="margin-top: -10px">
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                  <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingOne">
                      <h4 class="panel-title">
                        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                          系统管理
                        </a>
                      </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                      <div class="panel-body">
                        <ul class="list-group">
                              <li class="list-group-item"><a href="javascript:loadSystemMonitoring();">系统监控</a></li>
                              <li class="list-group-item"><a href="javascript:loadAccessRecords()">访问记录</a></li>
                              <%--<li class="list-group-item"><a href="#">XXXXXXX</a></li>--%>
                        </ul>
                      </div>
                    </div>
                  </div>
                  <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingTwo">
                      <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                          用户管理
                        </a>
                      </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                      <div class="panel-body">
                        <ul class="list-group">
                              <li class="list-group-item"><a href="javascript:loadAccountListTable();">用户列表</a></li>
                              <li class="list-group-item"><a href="javascript:loadOnlineUserListTable();">在线列表</a></li>
                             <%-- <li class="list-group-item"><a href="#">XXXXXXX</a></li>--%>
                        </ul>
                      </div>
                    </div>
                  </div>
                  <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingThree">
                      <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                          资源管理
                        </a>
                      </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                      <div class="panel-body">
                        <ul class="list-group">
                              <li class="list-group-item"><a href="javascript:allResourcesListTable();">资源列表</a></li>
                              <li class="list-group-item"><a href="javascript:resourcesReviewListTable();">资源审核</a></li>
                              <li class="list-group-item"><a href="javascript:resourcesReturnListTable();">退回记录</a></li>
                        </ul>
                      </div>
                    </div>
                  </div>
                </div>
            </div>
            <div class="col-md-10" style="margin-top:-8px" id="contextPage">

            </div>
        </div>
    </div>
<input value="${path}" id="path" hidden="hidden">
</body>
</html>
