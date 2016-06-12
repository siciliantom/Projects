<%--
  Created by IntelliJ IDEA.
  User: Kate
  Date: 28.02.2016
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="datatag" %>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="greetingtag" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title></title>
    <link href="<c:url value="/css/header.css" />" rel="stylesheet">
</head>
</head>
<body>
<div id="header">
    <span id="welcome_name"><greetingtag:greetingTag name="${name}"
                                                     locale="${locale}">${role}</greetingtag:greetingTag></span>
    <span class="hostel_name"><fmt:message key="label.header_name"/></span><br>
    <%--<span id="data"><fmt:message key="label.today"/><datatag:dataTag format=" dd.MM.yyyy"/></span><br>--%>
    <span id="role"><fmt:message key="label.role"/> ${role}</span><br>

    <form name="localeForm" method="POST" action="controller">
    <span class=locale>
        <select name="locale">
            <option value="ru_RU" ${selectRu}>Ru</option>
            <option value="en_US" ${selectEn}>En</option>
        </select>
        <input type="hidden" name="command" value="change_lang"/>
        <fmt:message key="button.change" var="changeButton"/>
        <input type="submit" name="change" value="${changeButton}"/>
         </span>
    </form>

    <form name="logoutForm" method="POST" action="controller">
        <span id="logout">
        <input type="hidden" name="command" value="logout"/>
        <fmt:message key="button.logout" var="logoutButton"/>
        <input type="submit" name="change" value="${logoutButton}"/>
             </span>
    </form>

</div>
</body>
</html>
