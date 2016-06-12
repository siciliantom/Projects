<%--
  Created by IntelliJ IDEA.
  User: Kate
  Date: 26.03.2016
  Time: 7:44
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
    <title><fmt:message key="title.login"/></title>
    <link href="<c:url value="/css/main_view.css"/>" rel="stylesheet">
</head>

<body>
<c:import url="/jsp/additional/header.jsp"/>
<div id="section">

    <span class="welcome"><fmt:message key="label.welcome_main"/>
    <span class="hostel">&nbsp<fmt:message key="label.hostel"/></span>!</span>
    <br><br><br><br><br><br><br>
    <span class="please"><fmt:message key="label.please"/></span><br>

    <form name="loginForm" method="POST" action="controller">
        <br><br>
        <table border="0" align="center">
            <tr>
                <td class="logpass"><fmt:message key="label.login"/><span class="asterisk"> *</span></td>
                <td><input class="field" type="text" required oninput="setCustomValidity('')"
                           oninvalid="this.setCustomValidity('<fmt:message key="label.fill"/>')" name="login"/></td>
            </tr>
            <tr>
                <td class="logpass"><fmt:message key="label.password"/><span class="asterisk"> *</span></td>
                <td><input class="field" type="password" required oninput="setCustomValidity('')"
                           oninvalid="this.setCustomValidity('<fmt:message key="label.fill"/>')" name="password"/></td>
            </tr>
            <tr>
                <td><a href="/controller?command=show_reg_form" name="command" value="showRegForm">
                    <fmt:message key="label.can_register"/></a></td>
                <td><input type="hidden" name="command" value="login"/>
                    <fmt:message key="button.log_in" var="nam"/>
                    <input class="button" type="submit" name="login" value="${nam}"/>
                </td>
            </tr>
        </table>
    </form>
    <div class="warning">
        <br><br>
        ${errorLoginPassMessage}
        <br/>
        ${wrongAction}
        <br/>
        ${nullPage}
        <br/>
    </div>
</div>
<c:import url="/jsp/additional/footer.jsp"/>
</body>
</html>