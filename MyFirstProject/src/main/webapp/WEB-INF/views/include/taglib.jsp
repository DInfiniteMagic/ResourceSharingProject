<%--
  Created by IntelliJ IDEA.
  User: 柒染
  Date: 2020/7/3
  Time: 18:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>

<c:set value="${pageContext.request.contextPath}/statics" var="ctxStatics"/>
<c:set value="${pageContext.request.contextPath}" var="path" />


<%String basePath=request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
<c:set value="<%=basePath%>" var="bPath" /><%--项目地址--%>