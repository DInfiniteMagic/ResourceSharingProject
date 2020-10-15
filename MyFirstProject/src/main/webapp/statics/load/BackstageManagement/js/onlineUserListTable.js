$(function () {
    var path=$("#path").val();//得到当前工程目录
    var lastClick=1;//上一次点击的行
    var nowClick;//本次点击
     $("#OnlineUserList_Table").jqGrid({/*属性*/
                styleUI:"Bootstrap",/*设置表格风格*/
                url:path+"/SystemManagement/initOnlineUserListTable",
                datatype:"json",//预期服务器返回结果类型
                mtype:"get",
                colNames: ["编号","用户名","用户ip","操作"],
                colModel:[
                    {name:"id",resizable:false,width: 80,sortable:false,},
                    {name:"username",resizable:false,sortable:false,width: 100},
                    {name:"ipHost",resizable:false,sortable:false,width: 130},
                    {sortable:false,resizable:false,formatter:function (cellvalue, options, rowObject){
                         var username=rowObject.username;
                         username=escape(username);
                        return "<button class='btn btn-danger  btn-xs' onclick='forceQuit(\""+username+"\")'>强退</button>";
                    }},
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
     $("#OnlineUserList_Table").setGridWidth($("#tableDiv").width());
    $(window).resize(function(){
            $("#OnlineUserList_Table").setGridWidth($("#tableDiv").width());
    });

});
  function forceQuit(username){
      username=unescape(username);
      $.ajax({
          url:path+"/SystemManagement/forceQuitOnlineUser",
          contentType:"text/xml",
          data:username,
          type:"POST",
          success:function () {
            bootbox.alert("已完成！");
            $("#OnlineUserList_Table").trigger("reloadGrid");
          }
      });
  }
