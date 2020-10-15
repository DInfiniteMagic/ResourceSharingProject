<%--
  Created by IntelliJ IDEA.
  User: 柒染
  Date: 2020/8/2
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div id="resourcesReviewList">
 <!--搜索框-->
<div class="row">
    <form class="form-inline " id="searchForm" style="margin-top: 60px">
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
     <div class="col-md-10 " id="tableDiv" style="padding-right:0px ">
         <table id="resourcesList" style="background-color: white;width: 100%">
        </table>
         <!--分页工具栏-->
         <div id="pager" style="background-color: white;height: 60px" ></div>
     </div>
    <div class="col-md-1"></div>
</div>

<!--显示详情的模态框-->
<!-- Button trigger modal -->
<!-- Modal -->
<div class="modal fade" id="showDetails" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">资源详情</h4>
      </div>
      <div class="modal-body">
        详情
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <!--<button type="button" class="btn btn-primary">Save changes</button>-->
      </div>
    </div>
  </div>
</div>
</div>
<script src="${ctxStatics}/load/BackstageManagement/js/resourcesReturnListTable.js"></script>