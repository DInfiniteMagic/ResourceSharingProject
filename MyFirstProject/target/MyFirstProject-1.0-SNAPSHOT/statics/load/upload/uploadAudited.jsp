<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="row col-sm-offset-2">
   <div class="col-sm-10">
       <h3 style="border-bottom: red solid 2px">已审核资源</h3>
   </div>
</div>
<div class="row" style="margin-top: 20px;" >
    <div class="col-md-2"></div>
     <div class="col-md-8 " id="tableDiv" style="padding-right:0px ">
         <table id="unreviewedList" style="background-color: white;width: 100%">
         </table>
         <!--分页工具栏-->
         <div id="UnreviewedPager" style="background-color: white;height: 50px;" ></div>
     </div>
    <div class="col-md-2"></div>
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
      <div class="modal-body" id="detailsDiv">
        详情
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <!--<button type="button" class="btn btn-primary">Save changes</button>-->
      </div>
    </div>
  </div>
</div>
<script src="${ctxStatics}/load/upload/js/uploadAudited.js"></script>