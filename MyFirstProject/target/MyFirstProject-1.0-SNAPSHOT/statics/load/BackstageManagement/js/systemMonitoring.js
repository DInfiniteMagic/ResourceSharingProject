
var ChartData=[
             {
                label: '内存使用率',
                data: [0, 0, 0, 0, 0, 0],
                backgroundColor: [
                    'rgba(54, 162, 235, 0)',
                ],
                borderColor: [
                    'rgba(116,195,116)',
                ],
                pointBorderColor:[
                    'rgba(116,195,116)',
                    'rgba(116,195,116)',
                    'rgba(116,195,116)',
                    'rgba(116,195,116)',
                    'rgba(116,195,116)',
                    'rgba(116,195,116)',
                    'rgba(116,195,116)',
                    'rgba(116,195,116)',
                    'rgba(116,195,116)',

                ],
                 pointBackgroundColor:[
                     'rgba(116,195,116)',
                    'rgba(116,195,116)',
                    'rgba(116,195,116)',
                    'rgba(116,195,116)',
                    'rgba(116,195,116)',
                    'rgba(116,195,116)',
                     'rgba(116,195,116)',
                     'rgba(116,195,116)',
                     'rgba(116,195,116)',


                 ],
                borderWidth: 1
            },
                {
                label: 'CPU使用率',
                data: [0, 0, 0, 0, 0, 0],
                backgroundColor: [
                    'rgba(54, 162, 235, 0)',
                ],
                borderColor: [
                    'rgba(114,200,226)',
                ],
                 pointBorderColor:[
                    'rgba(114,200,226)',
                    'rgba(114,200,226)',
                    'rgba(114,200,226)',
                    'rgba(114,200,226)',
                    'rgba(114,200,226)',
                    'rgba(114,200,226)',
                     'rgba(114,200,226)',
                     'rgba(114,200,226)',
                     'rgba(114,200,226)',
                ],
                 pointBackgroundColor:[
                    'rgba(114,200,226)',
                    'rgba(114,200,226)',
                    'rgba(114,200,226)',
                    'rgba(114,200,226)',
                    'rgba(114,200,226)',
                    'rgba(114,200,226)',
                     'rgba(114,200,226)',
                     'rgba(114,200,226)',
                     'rgba(114,200,226)',
                 ],
                borderWidth: 1
            },
                {
                label: '硬盘使用率',
                data: [0, 0, 0, 0, 0, 0],
                backgroundColor: [
                    'rgba(54, 162, 235, 0)',
                ],
                borderColor: [
                    'rgba(242,185,104)',
                ],
                 pointBorderColor:[
                    'rgba(242,185,104)',
                    'rgba(242,185,104)',
                    'rgba(242,185,104)',
                    'rgba(242,185,104)',
                    'rgba(242,185,104)',
                    'rgba(242,185,104)',
                     'rgba(242,185,104)',
                     'rgba(242,185,104)',
                     'rgba(242,185,104)',

                ],
                 pointBackgroundColor:[
                    'rgba(242,185,104)',
                    'rgba(242,185,104)',
                    'rgba(242,185,104)',
                    'rgba(242,185,104)',
                    'rgba(242,185,104)',
                    'rgba(242,185,104)',
                     'rgba(242,185,104)',
                     'rgba(242,185,104)',
                     'rgba(242,185,104)',
                 ],
                borderWidth: 1
            },
                {
                label: 'JVM使用率',
                data: [0, 0, 0, 0, 0, 0],
                backgroundColor: [
                    'rgba(54, 162, 235, 0)',
                ],
                borderColor: [
                    'rgba(217,83,79)',
                ],
                pointBorderColor:[
                    'rgba(217,83,79)',
                    'rgba(217,83,79)',
                    'rgba(217,83,79)',
                    'rgba(217,83,79)',
                    'rgba(217,83,79)',
                    'rgba(217,83,79)',
                    'rgba(217,83,79)',
                    'rgba(217,83,79)',
                    'rgba(217,83,79)',
                ],
                 pointBackgroundColor:[
                    'rgba(217,83,79)',
                    'rgba(217,83,79)',
                    'rgba(217,83,79)',
                    'rgba(217,83,79)',
                    'rgba(217,83,79)',
                    'rgba(217,83,79)',
                     'rgba(217,83,79)',
                     'rgba(217,83,79)',
                     'rgba(217,83,79)',
                 ],
                borderWidth: 1
            },
    ];
    var ctx=$("#lineChartForSystemMonitoringChart");
    var myChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: ['8小时前','7小时前','6小时前', '5小时前', '4小时前', '3小时前', '2小时前', '1小时前', '1小时内'],
        fill:false,
        datasets: [
            ChartData[0],ChartData[1],ChartData[2],ChartData[3],
        ]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true,
                },
                scaleLabel: {
                        display: true,
                        labelString: '百分比'
                }
            }]
        }
    }
    });
var path=$("#path").val();//得到当前工程目录

function getHistorySystemMonitorData() {
            $.ajax({
                url:path+"/SystemManagement/getHistorySystemMonitoring",
                dataType:"JSON",
                type: "GET",
                success:function (data) {
                    ChartData[0].data=data.rampercentageList;
                    ChartData[1].data=data.cpupercentageList;
                    ChartData[2].data=data.hardDiskPercentageList;
                    ChartData[3].data=data.jvmpercentageList;
                    myChart.update();
                }
            })
        };
 function getNowSystemMonitorData() {
        $.ajax({
            url:path+"/SystemManagement/getNowSystemMonitoring",
            dataType:"JSON",
            type: "GET",
            success:function (data) {
                closeLoading();//关闭loading
                var hardDiskPercentage=parseInt(data.hardDiskPercentage*100)+"%";//硬盘使用率
                var cpupercentage=parseInt(data.cpupercentage*100)+"%";//cpu使用率
                var jvmpercentage=parseInt(data.jvmpercentage*100)+"%";//jvm使用率
                var cpucount=data.cpucount;//cpu核数
                var rampercentage=parseInt(data.rampercentage*100)+"%";//内存使用率
                var osname=data.osname;//系统名称
                var jdkVersion=data.jdkVersion;//jdb版本
                var dataBase=data.dataBase;//数据库大小
                var ipAddress=data.ipAddress;//ip地址
                var macAddress=data.macAddress;//ip地址
                var startTime=data.startTime;//服务器启动时间
                var runTime=data.runTime;//服务器运行时间
                $("#RAMPercentageDiv").css("width",rampercentage);
                $("#RAMPercentageSpan").html(rampercentage);
                $("#CPUPercentageDiv").css("width",cpupercentage);
                $("#CPUPercentageSpan").html(cpupercentage);
                $("#HardDiskPercentageDiv").css("width",hardDiskPercentage);
                $("#HardDiskPercentageSpan").html(hardDiskPercentage);
                $("#JVMPercentageDiv").css("width",jvmpercentage);
                $("#JVMPercentageSpan").html(jvmpercentage);
                /*---------------------------------------------*/
                $("#osName").html(osname);
                $("#cpuSize").html(cpucount);
                $("#JDKVersion").html(jdkVersion);
                $("#dataBaseSize").html(dataBase);
                $("#ipAddress").html(ipAddress);
                $("#macAddress").html(macAddress);
                $("#startTime").html(startTime);
                $("#runTime").html(runTime);
            }
        })
    };

var  autoProgressBar;
var  autoLine;
$(function () {
     loading();//加载等待
     getNowSystemMonitorData();
     getHistorySystemMonitorData();
     start();
});

function start() {
    autoProgressBar=setInterval(getNowSystemMonitorData,3000);
    autoLine=setInterval(getHistorySystemMonitorData,3600000);
}

function loading() {
    var _width=$("#SystemMonitoring").width();
    var _height=$("#contextPage").height();
    //在页面未加载完毕之前显示的loading Html自定义内容
    var _LoadingHtml = '<div style="position: absolute;z-index: 10;width: 100%;height:100%;background-color:rgba(237,237,230,0.74);"><img class="col-sm-2  col-sm-offset-5" id="loadingImg"  src="../../../../statics/photo/loading.gif"/></div>';
    $("#loading").html(_LoadingHtml);
    $("#loadingImg").css("margin-top",_height*2/5);
}
function closeLoading() {
    $("#loading").css("display","none");
}