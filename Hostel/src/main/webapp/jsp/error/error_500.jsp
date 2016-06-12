<%--
  Created by IntelliJ IDEA.
  User: Kate
  Date: 11.03.2016
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>Error_500</title>
    <link href="<c:url value="/css/error.css"/>" rel="stylesheet"/>
</head>
<body>
<div class="crucial">
    <form name="backForm" method="POST" action="controller">
        <input type="hidden" name="command" value="back"/>
        <input class="back_button" type="submit" value=
        <fmt:message key="label.back"/> onclick="history.back()"/>
    </form>
    <br><br><br>
    Request from ${pageContext.errorData.requestURI} is failed
    <br>
    Servlet name: ${pageContext.errorData.servletName}
    <br>
    Status code: ${pageContext.errorData.statusCode}
    <br>
    Exception: ${pageContext.exception}
    <br>
    Message from exception: ${pageContext.exception.message}
</div>
</body>
</html>
