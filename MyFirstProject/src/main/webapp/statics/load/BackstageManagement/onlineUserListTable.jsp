<%--
  Created by IntelliJ IDEA.
  User: 柒染
  Date: 2020/7/30
  Time: 18:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div id="onlineUserList">

</div>
<div class="row" style="margin-top: 50px;" >
<div class="col-md-1"></div>
 <div class="col-md-10 " id="tableDiv" style="padding-right:0px ">
     <table id="OnlineUserList_Table" style="background-color: white;width: 100%!important;">
     </table>
     <!--分页工具栏-->
     <div id="pager" style="background-color: white;height: 60px" ></div>
 </div>
<div class="col-md-1"></div>
</div>
<!--用户列表js-->
<script src="${ctxStatics}/load/BackstageManagement/js/onlineUserListTable.js"></script>