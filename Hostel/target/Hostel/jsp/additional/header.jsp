<%--
  Created by IntelliJ IDEA.
  User: Kate
  Date: 24.02.2016
  Time: 13:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head><title>Header</title>
    <link href="<c:url value="/css/header.css" />" rel="stylesheet">
</head>
<body>
<div id="header">
    <span class="hostel_name"><fmt:message key="label.header_name"/></span><br>
    <br>

    <form name="localeForm" method="POST" action="controller">
        <span class="locale">
            <select name="locale">
                <option value="ru_RU" ${selectRu}>Ru</option>
                <option value="en_US" ${selectEn}>En</option>
            </select>
            <input type="hidden" name="command" value="change_lang"/>
        <fmt:message key="button.change" var="changeButton"/>
        <input type="submit" name="change" value="${changeButton}"/>

        </span>
    </form>
</div>
</body>
</html>