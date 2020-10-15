<%--
  Created by IntelliJ IDEA.
  User: 柒染
  Date: 2020/8/1
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript">
    $(function() {
            $.ajax({
                type:"POST",
                url:"${path}/user/isLogin",
                dataType:"json",
                success:function(data) {
                    if(data.name==null){//未登录

                    }else{
                        webSocket(data.name);
                    }
                }
            });//页面加载时去后台判断该账户是否登录
    });
      function webSocket(username) {
          var websocket;
        // 首先判断是否 支持 WebSocket
        Spath="ws://${bPath}";
     if('WebSocket' in window) {
         if(websocket==undefined) { websocket = new WebSocket(Spath+"/MessageHandler?username="+username);}
     } else if('MozWebSocket' in window) {
         if(websocket==undefined) { websocket = new MozWebSocket(Spath+"/testHandler?username="+username);}
     } else {
         if(websocket==undefined) { websocket = new SockJS(${path}+"/socketJs/MessageHandler?username="+username);}
     }

     // 打开连接时
     websocket.onopen = function(evnt) {
         console.log("  websocket.onopen  ");
     };

     // 收到消息时
     websocket.onmessage = function(evnt) {
         bootbox.alert(evnt.data);
     };

     websocket.onerror = function(evnt) {
         console.log("  websocket.onerror  ");
     };

     websocket.onclose = function(evnt) {
         console.log("  websocket.onclose  ");
     };
    }

    </script>
</head>
<body>


</body>
</html>
