<%--
  Created by IntelliJ IDEA.
  User: Kate
  Date: 18.02.2016
  Time: 7:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="title.register"/></title>
    <link href="<c:url value="/css/main_view.css" />" rel="stylesheet">
</head>
<body>
<c:import url="/jsp/additional/header.jsp"/>
<div id="section">
    <form name="backForm" method="POST" action="controller">
        <input type="hidden" name="command" value="back"/>
        <input class="back_button" type="submit" value=<fmt:message key="label.back"/>>
    </form>
    <span class="welcome"><fmt:message key="label.welcome_main"/>
    <span class="hostel">&nbsp<fmt:message key="label.hostel"/></span>!</span>
    <br><br><br><br><br>
    <span class="please"><fmt:message key="label.fill_register"/></span><br><br><br>

    <form name="registerForm" method="POST" action="controller">
        <table border="0" align="center">
            <tr>
                <td class="content"><fmt:message key="label.name"/><span class="asterisk"> *</span></td>
                <td><input class="field" type="text" value="${registration_client.name}" required
                           oninput="setCustomValidity('')"
                           oninvalid="this.setCustomValidity('<fmt:message key="label.fill"/>')"
                           name="name"><br></td>
            </tr>
            <tr>
                <td class="content"><fmt:message key="label.surname"/><span class="asterisk"> *</span></td>
                <td><input class="field" type="text" value="${registration_client.surname}" required
                           oninput="setCustomValidity('')"
                           oninvalid="this.setCustomValidity('<fmt:message key="label.fill"/>')" name="surname"><br>
                </td>
            </tr>
            <tr>
                <td class="content"><fmt:message key="label.country"/><span class="asterisk"> *</span></td>
                <td><input class="field" type="text" value="${registration_client.country}" required
                           oninput="setCustomValidity('')"
                           oninvalid="this.setCustomValidity('<fmt:message key="label.fill"/>')"
                           name="country"><br/></td>
            </tr>
            <tr>
                <td class="content"><fmt:message key="label.login"/><span class="asterisk"> *</span></td>
                <td><input class="field" type="text" value="${registration_client.authentication.login}" required
                           oninput="setCustomValidity('')"
                           oninvalid="this.setCustomValidity('<fmt:message key="label.fill"/>')"
                           name="login"><br/></td>
            </tr>
            <tr>
                <td class="content"><fmt:message key="label.password"/><span class="asterisk"> *</span></td>
                <td><input class="field" type="password" value="${registration_client.authentication.password}" required
                           oninput="setCustomValidity('')"
                           oninvalid="this.setCustomValidity('<fmt:message key="label.fill"/>')"
                           name="password"><br/></td>
            </tr>
            <tr>
                <td class="content"><fmt:message key="label.role"/><span class="asterisk"> *</span></td>
                <td><select class="field" name="client_role">
                    <option value="user" ${selectUser}><fmt:message key="label.user"/></option>
                    <option value="admin" ${selectAdmin}><fmt:message key="label.admin"/></option>
                </select><br>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><br><input type="hidden" name="command" value="register"/>
                    <input class="button" type="submit" name="register" value=<fmt:message key="button.register"/>></td>
            </tr>
        </table>
    </form>
    <div class="warning">
        ${warningClientExists}
        ${wrongRegistration}
        <br><br>
    </div>
</div>
<c:import url="/jsp/additional/footer.jsp"/>
</body>
</html>
