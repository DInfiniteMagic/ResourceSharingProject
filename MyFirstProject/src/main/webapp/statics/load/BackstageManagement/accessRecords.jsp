<%--
  Created by IntelliJ IDEA.
  User: 柒染
  Date: 2020/7/28
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div id="accessRecords">
<div class="row">
    <form class="form-inline " id="searchForm" style="margin-top: 30px">
        <div class="col-md-3"></div>
        <div class="form-group col-md-7">
            <div class="form-group col-md-8">
                <input type="text" style="width:100%;" class="form-control input-lg " id="searchInput" placeholder="搜索">
            </div>
            <div class="form-group col-md-2">
                <input type="button" class="btn btn-danger btn-lg btn-block " value="搜索" id="searchBtn">
            </div>
        </div>
    </form>
</div>
</div>
<div class="row" style="margin-top: 50px;" >
<div class="col-md-1"></div>
 <div class="col-md-10 " id="tableDiv" style="padding-right:0px ">
     <table id="accessRecordsList" style="background-color: white;width: 100%!important;">
     </table>
     <!--分页工具栏-->
     <div id="pager" style="background-color: white;height: 60px" ></div>
 </div>
<div class="col-md-1"></div>
</div>
<!--访问记录js-->
<script src="${ctxStatics}/load/BackstageManagement/js/accessRecords.js"></script>