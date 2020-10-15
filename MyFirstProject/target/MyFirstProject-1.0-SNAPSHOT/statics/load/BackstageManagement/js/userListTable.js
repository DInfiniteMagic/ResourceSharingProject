$(function () {
    var path=$("#path").val();//得到当前工程目录
    var lastClick=1;//上一次点击的行
    var nowClick;//本次点击
     $("#userList_Table").jqGrid({/*属性*/
                styleUI:"Bootstrap",/*设置表格风格*/
                url:path+"/SystemManagement/initUserListTable",
                datatype:"json",//预期服务器返回结果类型
                mtype:"get",
                colNames: ["编号","用户名","用户角色","创建时间","账户状态"],
                colModel:[
                    {name:"id",resizable:false,width: 80,},
                    {name:"username",resizable:false,sortable:false,width: 100,},
                    {name:"roleName",resizable:false,sortable:false,width: 130,editable : true,edittype : "select",
                        editoptions:{
                        dataUrl:path+"/resource/getAllAcoountRolesForJqgrid"/*获取角色选项*/
                     }
                    },
                    {name:"registrationDate",resizable:false},
                    {name:"status",resizable:false,width: 80,
                        editable : true,
                        edittype : "select",editoptions : {value : "正常:正常;冻结:冻结"}
                        ,formatter:function (cellvalue, options, rowObject) {
                        if(cellvalue=='正常'){
                            return "<span style='color: green'>"+cellvalue+"</span>"
                        }else{
                            return "<span style='color: red'>"+cellvalue+"</span>"
                        }
                    }}
                ],
                height:"100%",
                width:"100%",
                pager:"#pager",//设置分页工具栏html
                    // 注意: 1.一旦设置分页工具栏之后在根据指定url查询时自动向后台传递page(当前页) 和 rows(每页显示记录数)两个参数
                rowNum:6,//这个代表每页显示记录数
                rowList:[6,12,18],//生成可以指定显示每页展示多少条下拉列表
                viewrecords:true,//显示总记录数
                onSelectRow : function(id) {
                  if (id && id !== lastClick) {
                    nowClick=id;
                  }
                },
                editurl:path+"/SystemManagement/editAccountListTable"
        }).navGrid("#pager",{search:false,add:false,edit:false,del:false}
        );
     $("#userList_Table").setGridWidth($("#tableDiv").width());
    $(window).resize(function(){
            $("#userList_Table").setGridWidth($("#tableDiv").width());
    });
    $("#searchBtn").click(search);
    $('#searchInput').bind('keypress',function(event){
        var e = e || event;
        if(event.keyCode == "13"){
            search();
            return false;
         }
    });
    $("#editBtn").click(function() {
        if(checkSelect()){
            return;
        }
        $("#userList_Table").jqGrid('editRow',nowClick);
        this.disabled = 'true';
        $("#cancelBtn,#saveBtn").attr("disabled", false);
        lastClick=nowClick;
  });
  $("#saveBtn").click(function() {
        if(checkSelect()){
            return;
        }
        $("#userList_Table").jqGrid('saveRow', lastClick);
        $("#saveBtn,#cancelBtn").attr("disabled", true);
        $("#editBtn").attr("disabled", false);
        lastClick=nowClick;
  });
  $("#cancelBtn").click(function() {
        if(checkSelect()){
            return;
        }
        $("#userList_Table").jqGrid('restoreRow',lastClick);
        $("#saveBtn,#cancelBtn").attr("disabled", true);
        $("#editBtn").attr("disabled", false);
        lastClick=nowClick;
  });
  function  checkSelect() {
        if(nowClick==null){
            bootbox.alert("请选择一列！");
            return true;
      }
  }
});
function search() {
    var searchContext1=$("#searchInput").val();
    searchContext1=searchContext.trim();
    if(searchContext.length<1){
        bootbox.alert("搜索内容不能为空！");
        return;
    }
   $("#userList_Table").jqGrid("setGridParam",{
        url:path+"/SystemManagement/searchAccountListTable",
        mtype:"post",
        contentType:"json",
        postData:{"searchContext":searchContext1},
        page:1,
    }).trigger("reloadGrid");
}
