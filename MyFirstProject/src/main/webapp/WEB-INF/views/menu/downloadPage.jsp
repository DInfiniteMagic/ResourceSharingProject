<%--
  Created by IntelliJ IDEA.
  User: 柒染
  Date: 2020/7/19
  Time: 19:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>资源下载</title>
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
    <%--星空背景--%>
    <script src="${ctxStatics}/backgroundDynamic/js/StarrySky.js"></script>
    <link rel="stylesheet" href="${ctxStatics}/backgroundDynamic/css/StarrySky.css">
    <%--jqgrid--%>
    <!--jqgrid 核心-->
    <script src="${ctxStatics}/jqgrid/js/jquery.jqGrid.min.js"></script>
    <!--jqgrid 国际化-->
    <script src="${ctxStatics}/jqgrid/js/grid.locale-cn.js"></script>
    <!--jqgrid 适配bootstrap样式 css-->
    <link rel="stylesheet" href="${ctxStatics}/jqgrid/css/ui.jqgrid-bootstrap.css" />
    <!--jqgrid 核心 css-->
    <link rel="stylesheet" href="${ctxStatics}/jqgrid/css/ui.jqgrid.css" />
    <style>
        #list #choiceUl li :hover{
            fill: red;
            color: red;
        }
        thead #ui-jqgrid-labels tr {
            height: 30px;
        }
       #ui-jqgrid-hdiv #ui-jqgrid-hbox{
           height: 30px;
       }
        .ui-jqgrid-view {
            font-size: 20px;
        }/*表格字体大小*/
         .ui-jqgrid .ui-jqgrid-htable th {
            height: 40px;
            font-size: 20px;
             background-color: #D9534F;
             border: 1px solid white;
         }/*标题*/
        .ui-jqgrid .ui-jqgrid-htable th div:hover{
            color: black!important;
        }
         .ui-jqgrid .ui-jqgrid-htable th div{
             height: 40px!important;
             line-height: 40px;
             color: white;
         }/*标题*/
        .ui-jqgrid tr.ui-row-ltr td {
            text-align: center;
        }
        .ui-jqgrid .ui-jqgrid-bdiv{ overflow-x: hidden; border:none}
        .ui-jqgrid-hdiv .ui-jqgrid-hbox table {
            border-bottom:1px solid;
        }
        #pager table{
            height: 45px;
        }
        #pager table td span{
            font-size: 18px;
        }
        #input_pager input{
            height: 20px;
            font-size: 15px;
        }
        #input_pager span{
            font-size: 15px;
        }
        #sp_1_pager{/*共x页*/
            font-size: 15px!important;
        }
        #pager select{
            height: 20px!important;
        }

        .ui-jqgrid-hbox .s-ico span{
            margin-top: 3px;
            display: block;
            height:25px;
        }
        .ui-jqgrid-hbox .s-ico .glyphicon{
            font-size: 12px!important;
        }
        #operation{
            font-size: 17px;
        }
        .ui-jqgrid tr.jqgrow td {
            text-overflow : ellipsis;
        }
        .ui-jqgrid tr.jqgrow td {
            vertical-align:text-top;
            display: table-cell;
        }
    </style>
    <script>
        var isLogin=false;
        $(function () {
            //表格初始化
            $("#resourcesList").jqGrid({/*属性*/
                styleUI:"Bootstrap",/*设置表格风格*/
                url:"${path}/resources/initDownloadTable",
                datatype:"json",//预期服务器返回结果类型
                mtype:"post",
                colNames: ["编号","资源名称","所属类别","所需积分","上传者","资源大小","上传时间","下载次数", "资源详情","收藏","下载"],
                colModel:[
                    {name:"id",resizable:false,align:"center"},
                    {name:"downloadResourceName",resizable:false,sortable:false},
                    {name:"resourceClass",resizable:false,sortable:false},
                    {name:"resourceValue",width:120,resizable:false},
                    {name:"downloadAccountName",resizable:false,sortable:false},
                    {name:"resourceSize",resizable:false,formatter:function(cellvalue, options, rowObject) {
                            var size=parseFloat(cellvalue);
                            size = Math.round((size/(1024*1024))*100)/100;
                            if (size<=0.01){
                                size=0.01;
                            }
                            return size+"MB";
                     }},
                    {name:"uploadTime",resizable:false},
                    {name:"resourceDownloadTimes",resizable:false},
                    {name:"resourceDetails",resizable:false,width:120,sortable:false,formatter:function (cellvalue, options, rowObject) {
                        cellvalue=escape(cellvalue);
                        return "<button onclick='showDetails(\""+cellvalue+"\")' type='button' class='btn btn-success  btn-block' data-toggle='modal' data-target='#showDetails'>详情</button>"
                    }},
                    {name:"favorites",resizable:false,sortable:false,width:120,formatter:function (cellvalue, options, rowObject) {
                         var id=rowObject.id;
                        if(cellvalue==""){
                            var content=
                                "<button onclick='favorites("+id+","+cellvalue+")' type=\"button\" class=\"btn btn-default btn-block\">" +
                                "  <span  class=\"glyphicon glyphicon-star-empty\" aria-hidden=\"true\"></span>收藏" +
                                "</button>"
                            return content;
                        }else{
                            var content=
                                "<button onclick='favorites("+id+","+cellvalue+")'  type=\"button\" class=\"btn btn-default btn-block\">" +
                                "  <span  class=\"glyphicon glyphicon-star\" aria-hidden=\"true\"></span>收藏" +
                                "</button>"
                            return content;
                        }
                    }},
                    {resizable:false,sortable:false,width:160,width:120,formatter:function (cellvalue, options, rowObject) {
                        var id=rowObject.id;
                        return "<button onclick='download("+id+")' type=\"button\" class=\"btn btn-info btn-block\">" +
                                "  <span class=\"glyphicon glyphicon-download-alt\" aria-hidden=\"true\"></span>下载" +
                                "</button>"
                    }}
                ],
                height:"100%",
                width:"100%",
                pager:"#pager",//设置分页工具栏html
                    // 注意: 1.一旦设置分页工具栏之后在根据指定url查询时自动向后台传递page(当前页) 和 rows(每页显示记录数)两个参数
                rowNum:6,//这个代表每页显示记录数
                rowList:[6,12,24],//生成可以指定显示每页展示多少条下拉列表
                viewrecords:true,//显示总记录数
                loadComplete: function(data) {
                    isLogin=data.login;//获取登录状态
                },
            });
            $(window).resize(function(){
                $("#resourcesList").setGridWidth($("#tableDiv").width());
            });

            $("#searchBtn").click(search);

            $('#searchInput').bind('keypress',function(event){
                var e = e || event;
                if(event.keyCode == "13"){
                    search();
                    return false;
                 }
            });
        })
        function search() {
                var searchContext=$("#searchInput").val();
                searchContext=searchContext.trim();
                if(searchContext.length<1){
                    bootbox.alert("搜索内容不能为空！");
                    return;
                }
               $("#resourcesList").jqGrid("setGridParam",{
                    url:"${path}/resources/searchDownloadTable",
                    mtype:"post",
                    contentType:"json",
                    postData:{"searchContext":searchContext},
                    page:1,
                }).trigger("reloadGrid");
            }
        function  showDetails(value) {
            value=unescape(value);
            value=value.replace(/[\r\n]/g,"</br>");
            $(".modal-body").html(value);
        }
        function download(id) {
           if(isLogin==false){
              bootbox.alert("请登录后再下载!");
              return;
            }
           window.location.href="${path}/resources/downloadResources?id="+id;
        }
        function favorites(id,favorites) {
            if(isLogin==false){
              bootbox.alert("请登录!");
              return;
            }
            $("#resourcesList").jqGrid("setGridParam",{
                url:"${path}/resources/setFavorites",
                mtype: "post",
                postData:{"id":id,"favorites":favorites},
                datatype: "json"
            }).trigger("reloadGrid");
        }

    </script>
</head>
<body>
    <canvas id="canvas"></canvas>
    <jsp:include page="head.jsp" flush="true"></jsp:include>
    <jsp:include page="webSocket.jsp" flush="true"></jsp:include>
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
        <div class="col-md-1"></div>
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
</body>
</html>
