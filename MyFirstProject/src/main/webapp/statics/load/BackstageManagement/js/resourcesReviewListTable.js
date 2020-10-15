var isLogin=false;
var path=$("#path").val();//得到当前工程目录
var lastClick=1;//上一次点击的行
var nowClick;//本次点击
$(function () {
    //表格初始化
    $("#resourcesList").jqGrid({/*属性*/
        styleUI:"Bootstrap",/*设置表格风格*/
        url:path+"/SystemManagement/initResourcesReviewTable",
        datatype:"json",//预期服务器返回结果类型
        mtype:"post",
        colNames: ["编号","资源名称","所属类别","所需积分","上传者","资源大小","上传时间","下载次数", "审核状态","资源详情","下载"],
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
            {name:"resourcesStatue",resizable:false,editable : true,edittype : "select",editoptions:{value: "已审核:已审核;未审核:未审核;退回:退回"},
                formatter:function (cellvalue, options, rowObject) {
                    if (cellvalue ==null) {
                        return "<span style='color: red'>未审核</span>"
                    }
                }
            },
            {name:"resourceDetails",resizable:false,width:120,sortable:false,formatter:function (cellvalue, options, rowObject) {
                cellvalue=escape(cellvalue);
                return "<button onclick='showDetails(\""+cellvalue+"\")' type='button' class='btn btn-success  btn-block' data-toggle='modal' data-target='#showDetails'>详情</button>"
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
        editurl:path+"/SystemManagement/editResourcesReviewTable",//编辑的url
        loadComplete: function(data) {
            isLogin=data.login;//获取登录状态
        },
        onSelectRow:function(id) {
              if (id && id !== lastClick) {
                nowClick=id;
              }
        },
    }).navGrid("#pager",{search:false,add:false},{closeAfterEdit: true/*添加完后关闭面板*/,reloadAfterSubmit:true});
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
            url:path+"/resources/searchDownloadTable",
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
   window.location.href=path+"/resources/downloadResources?id="+id;
}
