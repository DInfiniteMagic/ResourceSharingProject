var flagfileUpload=false;//判断 第一选择文件
function change() {
    var uploadInput=$("#fileUpload").val();//判断inputFile是否上传了文件
    if(uploadInput!=""){
        flagfileUpload=true;
        var obj = document.getElementById("fileUpload");
        var len = obj.files.length;
        var temp = obj.files[0].name;//获取上传文件名称
        var a1=$("#fileNameA");
        var a2=$("#renewUpload");
        a1.html(temp);
        a2.html("重新上传");
        $("#addSvg").css("display","none");
        $("#fileSvg").css("display","");
        $("#errorSvg").css("display","none");
        if($("#resourceName").val()==''){//当上传资源名称未填写时以文件名代替
            $("#resourceName").val(temp);//将上传资源名写入资源框中
        }
        $("#fileInformationForm").bootstrapValidator('updateStatus', 'resourceName', 'NOT_VALIDATED').bootstrapValidator('validateField', 'resourceName');
    }
}
$(function () {
    checkFileUpload("#fileInformationForm");//上传信息校验
    $("#ToSubmit").click(function () {
        $("#fileInformationForm").bootstrapValidator(checkFileUploadVar);
        $("#fileInformationForm").data('bootstrapValidator').validate();
        var flag=$("#fileInformationForm").data('bootstrapValidator').isValid();//判断表单是否验证
        var uploadInput=$("#fileUpload").val();//判断inputFile是否上传了文件
        if(uploadInput=="" && !flagfileUpload){
            $("#errorSvg").css("display","block");
            $("#addSvg").css("display","none");
            $("#fileSvg").css("display","none");
        }
        //console.log(flag==true && (uploadInput!="" || flagfileUpload));
        if(flag==true && (uploadInput!="" || flagfileUpload)){//当表单被验证,切文件上传了才能提交表单
            //$("#fileInformationForm").data('bootstrapValidator').defaultSubmit();
            //$("#fileInformationForm").submit();
            upgrade();
        }
    });
    !function () {
         var path=$("#path").val();//得到当前工程目录
        $.ajax({
            url:path+"/resource/getResourcesClass",
            type: "GET",
            success:function (data) {
                $("#resourceClass").html(data);
            }
        })
    }();/*立即执行函数*/
});
function upgrade() {
    var formdata = document.getElementById("fileInformationForm");
    var formData = new FormData(formdata);
    var path=$("#path").val();//得到当前工程目录
    path=path+"/resources/upload";
    $("#progressDiv").show();//显示进度条
    // console.log(formData, file)
    $.ajax({
        type: 'post',
        url: path,
        data: formData,
        //上传文件下面这三个一定要加上
        cache: false,
        contentType: false,
        processData: false,
        xhr: function () {//这里是计算上传进度

            myXhr = $.ajaxSettings.xhr();
            if (myXhr.upload) {
                myXhr.upload.addEventListener('progress', function (e) {
                    if (e.lengthComputable) {
                        var percent = Math.floor(e.loaded / e.total * 100);
                        if(percent==100){
                            $("#showProgress").html("请稍等！");//显示百分比
                        }
                        else{
                            $("#showProgress").html(percent+"%");//显示百分比
                        }
                        $("#showProgress").attr("aria-valuenow",percent);
                        $("#showProgress").css("width",percent+"%");

                    }
                }, false);
            }
            return myXhr;
        },
        success: function (res) {
            $("#progressDiv").hide();//隐藏进度条
            bootbox.alert({
                message: res,
                callback: function () {
                    window.location.href=$("#path").val()+"/resources/uploadPageRedirect";
                }
            });
        }
    })
}
