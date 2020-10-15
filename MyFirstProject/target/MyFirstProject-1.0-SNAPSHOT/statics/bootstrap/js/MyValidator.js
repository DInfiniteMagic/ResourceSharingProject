var validatorObj={
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        username: {
            message: '用户名验证失败',
            validators: {
                notEmpty: {
                    message: '用户名不能为空'
                },
                stringLength:{
                    min:6,
                    max:18,
                    message:'用户名长度必须在6到18位之间'
                },
                regexp:{
                    regexp: /^[a-zA-Z0-9_]+$/,
                    message: '用户名只能包含大写、小写、数字和下划线'
                },
                /*此处要添加一个ajax 做用户名是否存在的判断*/
                callback:{
                    delay: 3000,
                    message:"服务器异常",
                    callback:function (value,validator) {
                        var length=value.toString().length;
                        var returnDate;
                        if(length>=6 && length<=18){/*限定发送ajax请求的条件*/
                            $.ajax({
                                type: "POST",
                                url:$('#path').val()+'/tourist/checkSameUsername',
                                data:value,
                                async:false,/*同步加载*/
                                contentType:"text/xml",
                                success:function (data) {//如果有重名 回返回false
                                    returnDate=data;
                                }
                            });
                        }
                        if(returnDate==false){
                            return {"vaild":false,message:"此用户名已存在！"};/*此处是一个jquery对象*/
                        }
                        return true;
                    }
                }
            }
        },
        mailbox: {
            message: '邮箱验证失败',
            validators: {
                notEmpty: {
                    message: '邮箱不能为空'
                },
                regexp:{
                    regexp:/[\w!#\$%&amp;'\*\+\-\/=\^_`{\|}~.]+@([\w-]+\.)+(com|net|cn|org|me|cc|biz)$/,
                    message:"邮箱地址格式有误或不支持此类型邮箱！"
                }
            }
        },
        phoneNum:{
            message: '电话验证失败',
            validators: {
                notEmpty: {
                    message: '电话不能为空'
                },
                phone:{
                    message:'请输入正确的电话号码',
                    country:'CN'/*当前验证号码的归属国家*/
                }
            }
        },
        name:{
            message: '昵称验证失败',
            validators: {
                notEmpty:{
                    message:'昵称不能为空',
                },
                stringLength:{
                    min:2,
                    max:10,
                    message:'昵称长度必须在2到10位之间'
                }
            }
        },
        password:{
            message:'密码验证失败',
            validators: {
                notEmpty:{
                    message:'密码不能为空',
                },
                stringLength:{
                    min:6,
                    max:18,
                    message:'密码长度必须在6到18位之间!'
                },
                callback:{//自定义验证
                    callback:function (value,validator) {
                        var pattern=/^(?:\d+|[a-zA-Z]+|[!@#$%^&*]+)$/;//纯数字，或者字符
                        var pattern1=/^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*]+$)[a-zA-Z\d!@#$%^&*]+$/;//字母+数字，字母+特殊字符，数字+特殊字符
                        var pattern2=/^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*]+$)(?![a-zA-z\d]+$)(?![a-zA-z!@#$%^&*]+$)(?![\d!@#$%^&*]+$)[a-zA-Z\d!@#$%^&*]+$/;//字母+数字+特殊字符
                        var flag=$('#checkPassword').val();
                        if(flag!=""){/*当checkPassword不为空是时 主动验证checkPassword */
                            $('form').bootstrapValidator('updateStatus','checkPassword','NOT_VALIDATED').bootstrapValidator('validateField','checkPassword' );
                        }
                        if(pattern.exec(value) && value.toString().length>=6 && value.toString().length<=18){
                            return {vaild:false,message:"当前密码强度较弱！"}
                        }/*else if((pattern2.exec(value) || pattern1.exec(value)) &&  value.toString().length>=6 && toString().length<=18){
                                return true; //此处作为后期 改进版 显示 密码强弱时使用
                            }*/else{
                            return true;
                        }
                    }
                },
                different: {
                    field: 'username',
                    message: '密码不能同账号一致'
                }
            }
        },
        checkPassword:{
            validators:{
                notEmpty: {
                    message: '密码重复不能为空'
                },
                identical: {
                    field: 'password',
                    message: '两次输入的密码不相符'
                }
            }
        },
        captcha:{
            message:'验证码校验失败',
            // threshold:4,  /*满足4字节发送 ,但如果设置如此将会出现4字节之前不验证  后期需要改进*/
            validators:{
                notEmpty: {
                    message: '验证码不能为空'
                },
                stringLength:{
                    message:'验证码长度为4位',
                    min:4,
                    max:4
                },
                remote:{//ajax验证。server result:{"valid",true or false}
                    url:$('#path').val()+'/../../resource/checkCaptcha',
                    type:'POST',
                    message:'验证码错误！',
                    delay: 1000,//ajax刷新的时间是1秒一次
                    /*发送的数据格式类似  inputName=value  例如captcha=asdc*/
                }
            }
        }
    }
};

function validator() {
    $('form').bootstrapValidator(validatorObj);
}

function checkActivationCode() {
    $("#activationCodeForm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            activationCode:{
                message:"激活码验证失败",
                validators: {
                    notEmpty: {
                        message: "激活码不为空",
                    },
                    stringLength:{
                        message: "激活码长度为6位",
                        max:6,
                        min:6
                    }
                }
            }
        }
    })
}


function checkMail(formName) {
   $(formName).bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            mailbox: {
                message: '邮箱验证失败',
                validators: {
                    notEmpty: {
                        message: '邮箱不能为空'
                    },
                    regexp:{
                        regexp:/[\w!#\$%&amp;'\*\+\-\/=\^_`{\|}~.]+@([\w-]+\.)+(com|net|cn|org|me|cc|biz)$/,
                        message:"邮箱地址格式有误或不支持此类型邮箱！"
                    }
                }
            }
        }
   });
}

function  checkForgetPassword(form) {
    $(form).bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            forgetPassword_username:{
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    stringLength:{
                        min:6,
                        max:18,
                        message:'用户名长度必须在6到18位之间'
                    },
                    regexp:{
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '用户名只能包含大写、小写、数字和下划线'
                    },
                    /*此处要添加一个ajax 做用户名是否存在的判断*/
                    callback:{
                        delay: 3000,
                        message:"服务器异常",
                        callback:function (value,validator) {
                            var length=value.toString().length;
                            var returnDate;
                            if(length>=6 && length<=18){/*限定发送ajax请求的条件*/
                                $.ajax({
                                    type: "POST",
                                    url:$('#path').val()+'/tourist/checkSameUsername',
                                    data:value,
                                    async:false,/*同步加载*/
                                    contentType:"text/xml",
                                    success:function (data) {//如果有重名 回返回false
                                        returnDate=data;
                                    }
                                });
                            }
                            if(returnDate==true){
                                return {"vaild":false,message:"此用户名不存在！"};/*此处是一个jquery对象*/
                            }
                            return true;
                        }
                    }
                }
            },
            newPassword:{
                message:'密码验证失败',
                validators: {
                    notEmpty:{
                        message:'密码不能为空',
                    },
                    stringLength:{
                        min:6,
                        max:18,
                        message:'密码长度必须在6到18位之间!'
                    },
                    callback:{//自定义验证
                        callback:function (value,validator) {
                            var pattern=/^(?:\d+|[a-zA-Z]+|[!@#$%^&*]+)$/;//纯数字，或者字符
                            var pattern1=/^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*]+$)[a-zA-Z\d!@#$%^&*]+$/;//字母+数字，字母+特殊字符，数字+特殊字符
                            var pattern2=/^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*]+$)(?![a-zA-z\d]+$)(?![a-zA-z!@#$%^&*]+$)(?![\d!@#$%^&*]+$)[a-zA-Z\d!@#$%^&*]+$/;//字母+数字+特殊字符
                            var flag=$("#checkNewPassword").val();

                            if(flag!=""){/*当checkNewPassword不为空是时 主动验证checkNewPassword */
                                $(form).bootstrapValidator('updateStatus','checkNewPassword','NOT_VALIDATED').bootstrapValidator('validateField','checkNewPassword' );
                            }
                            if(pattern.exec(value) && value.toString().length>=6 && value.toString().length<=18){
                                return {vaild:false,message:"当前密码强度较弱！"}
                            }/*else if((pattern2.exec(value) || pattern1.exec(value)) &&  value.toString().length>=6 && toString().length<=18){
                                return true; //此处作为后期 改进版 显示 密码强弱时使用
                            }*/else{
                                return true;
                            }
                        }
                    },
                    different: {
                        field: 'username',
                        message: '密码不能同账号一致'
                    }
                }
            },
            checkNewPassword:{
                validators:{
                    notEmpty: {
                        message: '密码重复不能为空'
                    },
                    identical: {
                        field: 'newPassword',
                        message: '两次输入的密码不相符'
                    }
                }
            },
            validatorCode:{
                message:"激活码验证失败",
                validators: {
                    notEmpty: {
                        message: "激活码不为空",
                    },
                    stringLength:{
                        message: "激活码长度为6位",
                        max:6,
                        min:6
                    }
                }
            }
        }
    });
}
var checkFileUploadVar={
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon',
        invalid: 'glyphicon',
        validating: 'glyphicon'
    },
    fields: {
        resourceName:{
            message:"资源名称校验失败",
            validators: {
                notEmpty: {
                    message: "资源名称名不为空",
                },
                stringLength:{
                    min:6,
                    max:50,
                    message:"资源名称6-50个字符"
                }
            }
        },
        resourceClass:{
            message:"资源类型校验失败",
            validators: {
                notEmpty: {
                    message: "资源类型不为空",
                }
            }
        },
        resourceDescription:{
            message:"资源描述校验失败",
            validators: {
                notEmpty: {
                    message: "资源描述不为空",
                },
                stringLength:{
                    min:25,
                    max:800,
                    message:"资源描述字数在25-800字之间"
                }
            }
        }
    }
};
function checkFileUpload(formName) {
    $(formName).bootstrapValidator(checkFileUploadVar);
}

