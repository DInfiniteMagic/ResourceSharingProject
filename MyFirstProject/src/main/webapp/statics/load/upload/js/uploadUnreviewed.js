$(function () {
            var path=$("#path").val();//得到当前工程目录
            $("#unreviewedList").jqGrid({
                styleUI:"Bootstrap",/*设置表格风格*/
                url:path+"/resources/initUploadTable",
                datatype:"json",//预期服务器返回结果类型
                mtype:"post",
                colNames: ["编号","资源名称","所属类别","所需积分","上传时间", "资源详情"],
                colModel:[
                     {name:"id",resizable:false},
                     {name:"resourceName",resizable:false,sortable:false,editable:true,
                         editrules:{edithidden:true,required:true,custom:true,custom_func:ValidatorResourcesName}
                     },
                     {name:"resourceClass",resizable:false,sortable:false,editable:true,
                         editrules:{edithidden:true,required:true},
                        edittype:"select",editoptions:{
                        dataUrl:path+"/resource/getResourcesClassForJqgrid"
                     }
                     },
                     {name:"resourceValue",resizable:false,
                         editable:true,edittype:"select",editoptions: {//本地写死的数据
                            value:"1:1;2:2;3:3;4:4;5:5"
                         }
                     },
                     {name:"uploadTime",resizable:false},
                     {name:"resourceDescription",resizable:false,width:120,sortable:false,
                         editable:true,edittype:"textarea",
                         editrules:{edithidden:true,required:true,custom:true,custom_func:ValidatorResourcesClass},
                         formatter:function (cellvalue, options, rowObject) {
                         cellvalue=escape(cellvalue);
                         return "<button value='"+cellvalue+"' onclick='showDetails(\""+cellvalue+"\")' type='button' class='btn btn-success  btn-block' data-toggle='modal' data-target='#showDetails'>详情</button>"
                    },
                         unformat:function (cellvalue, options, rowObject ) {
                            return $('button', rowObject).val();
                         }
                     }
                ],
                autowidth: true,
                height:"100%",
                width:"100%",
                pager:"#UnreviewedPager",//设置分页工具栏html
                rowNum:6,//这个代表每页显示记录数
                rowList:[6,12,24],//生成可以指定显示每页展示多少条下拉列表
                viewrecords:true,//显示总记录数
                editurl:path+"/resources/editUploadTable"//增删改的url
            }).navGrid("#UnreviewedPager",{search:false,add:false},
                {closeAfterEdit: true/*添加完后关闭面板*/,reloadAfterSubmit:true}
                );
            $(window).resize(function(){
                $("#unreviewedList").setGridWidth($("#tableDiv").width());
            });

        });
         function  showDetails(value) {
            value=unescape(value);
            value=value.replace(/[\r\n]/g,"</br>");
            $("#detailsDiv").html(value);
        }
        function ValidatorResourcesName(value,name) {
             value=value.trim();
             if(value.length<6 || value.length>50){
                 return [false,name+"6-50个字符"]
             }
             return [true,""];
        }
        function ValidatorResourcesClass(value,name) {
             value=value.trim();
             if(value.length<25 || value.length>800){
                 return [false,name+"25-800个字符"]
             }
             return [true,""];
        }