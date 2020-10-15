<%--
  Created by IntelliJ IDEA.
  User: 柒染
  Date: 2020/7/26
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div id="SystemMonitoring">
<div id="loading" class="row"></div>
<div class="row">
    <div class="col-md-6">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title"><span class="glyphicon glyphicon-time">历史系统状态</span></h3>
          </div>
          <div class="panel-body" id="lineChartForSystemMonitoring">
            <canvas id="lineChartForSystemMonitoringChart" width="100%" height="35px"></canvas>
          </div>
        </div>
    </div>
    <div class="col-md-6">
        <ul class="list-group">
          <li class="list-group-item list-group-item-success" >系统名称<span class="badge" id="osName">14</span></li>
          <li class="list-group-item list-group-item-info">CPU核数<span class="badge" id="cpuSize">14</span></li>
          <li class="list-group-item list-group-item-warning">JDK版本<span class="badge" id="JDKVersion">14</span></li>
          <li class="list-group-item list-group-item-danger">数据库大小<span class="badge" id="dataBaseSize">14</span></li>
          <li class="list-group-item list-group-item-success" >服务器Ip<span class="badge" id="ipAddress">14</span></li>
          <li class="list-group-item list-group-item-info">服务器MAC<span class="badge" id="macAddress">14</span></li>
          <li class="list-group-item list-group-item-warning">启动时间<span class="badge" id="startTime">14</span></li>
          <li class="list-group-item list-group-item-danger">运行时间<span class="badge" id="runTime">14</span></li>
        </ul>
    </div>
</div>
<div class="row">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h3 class="panel-title"><span class="glyphicon glyphicon-cog"></span>当前系统状态</h3>
      </div>
      <div class="panel-body">
        <label>内存使用率:</label>
        <div class="progress">
          <div id="RAMPercentageDiv" class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
            <span id="RAMPercentageSpan">100%</span>
          </div>
        </div>
        <label>CPU使用率:</label>
        <div class="progress">
          <div id="CPUPercentageDiv" class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width:100%">
            <span id="CPUPercentageSpan" >100%</span>
          </div>
        </div>
        <label>硬盘使用率:</label>
        <div class="progress">
          <div id="HardDiskPercentageDiv" class="progress-bar progress-bar-warning progress-bar-striped" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
            <span id="HardDiskPercentageSpan" >100%</span>
          </div>
        </div>
         <label>JVM使用率:</label>
        <div class="progress">
          <div id="JVMPercentageDiv" class="progress-bar progress-bar-danger progress-bar-striped" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
            <span id="JVMPercentageSpan">100%</span>
          </div>
        </div>
      </div>
    </div>
</div>
</div>
<!--系统监控js-->
<script src="${ctxStatics}/load/BackstageManagement/js/systemMonitoring.js"></script>

