$(function () {
    var path=$("#path").val();//得到当前工程目录
    $("#accessRecordsList").jqGrid({/*属性*/
                styleUI:"Bootstrap",/*设置表格风格*/
                url:path+"/SystemManagement/initAccessRecordsTable",
                datatype:"json",//预期服务器返回结果类型
                mtype:"get",
                colNames: ["编号","用户名","Ip地址","用户角色","操作时间","URL","操作内容"],
                colModel:[
                    {name:"id",resizable:false,width: 80,},
                    {name:"userAccount",resizable:false,sortable:false,width: 100,},
                    {name:"ipHost",resizable:false,sortable:false,width: 130,},
                    {name:"userRole",resizable:false,sortable:false,width: 80,formatter:function (cellvalue, options, rowObject) {
                            if(cellvalue=="admin"){
                                return "<span style='color:red'>"+cellvalue+"</span>"
                            }else if(cellvalue=="superUser"){
                                return "<span style='color:#5bc0de'>"+cellvalue+"</span>"
                            }else{
                                return cellvalue;
                            }
                        }
                    },
                    {name:"operateTime",resizable:false},
                    {name:"moduleType",resizable:false,sortable:false},
                    {name:"operateContent",resizable:false,sortable:false},
                ],
                height:"100%",
                width:"100%",
                pager:"#pager",//设置分页工具栏html
                    // 注意: 1.一旦设置分页工具栏之后在根据指定url查询时自动向后台传递page(当前页) 和 rows(每页显示记录数)两个参数
                rowNum:6,//这个代表每页显示记录数
                rowList:[6,12,18],//生成可以指定显示每页展示多少条下拉列表
                viewrecords:true,//显示总记录数
        }).navGrid("#pager",{search:false,add:false,edit:false,del:false}
        );
     $("#accessRecordsList").setGridWidth($("#tableDiv").width());
    $(window).resize(function(){
            $("#accessRecordsList").setGridWidth($("#tableDiv").width());
    });

    $("#searchBtn").click(search);
    $('#searchInput').bind('keypress',function(event){
        var e = e || event;
        if(event.keyCode == "13"){
            search();
            return false;
         }
    });
});
function search() {
    var searchContext=$("#searchInput").val();
    searchContext=searchContext.trim();
    if(searchContext.length<1){
        bootbox.alert("搜索内容不能为空！");
        return;
    }
   $("#accessRecordsList").jqGrid("setGridParam",{
        url:path+"/SystemManagement/searchAccessRecordsTable",
        mtype:"post",
        contentType:"json",
        postData:{"searchContext":searchContext},
        page:1,
    }).trigger("reloadGrid");
}
