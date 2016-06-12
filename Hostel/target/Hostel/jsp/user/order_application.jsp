<%--
  Created by IntelliJ IDEA.
  User: Kate
  Date: 26.03.2016
  Time: 7:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="title.order"/></title>
    <link href="<c:url value="/css/main_view.css" />" rel="stylesheet">
</head>
<body>
<c:import url="/jsp/additional/welcome_header.jsp"/>
<div id="section">
    <form name="backForm" method="POST" action="controller">
        <input type="hidden" name="command" value="back"/>
        <input class="back_button" type="submit" value=<fmt:message key="label.back"/>>
    </form>
    <br>
    <span class="price"><fmt:message key="label.price"/></span>
    <br><br><br><br>
    <span class="please"><fmt:message key="label.fill_order"/></span>
    <br><br><br><br>
    <form name="registerForm" method="POST" action="controller">
        <table border="0" align="center">
            <tr>
                <td class="content"><fmt:message key="label.places"/>:<span class="asterisk"> *</span></td>
                <td><input class="field" type="number" min="1" max="8" value="1" required
                           oninput="setCustomValidity('')"
                           oninvalid="setCustomValidity('<fmt:message key="label.fill_number"/>')" name="places"/><br>
                </td>
            </tr>
            <tr>
                <td class="content"><fmt:message key="label.arrival"/>:<span class="asterisk"> *</span></td>
                <td><input class="field" type="date" min="${today}" value="${today}"
                           required oninput="setCustomValidity('')"
                           oninvalid="this.setCustomValidity('<fmt:message key="label.fill"/>')"
                           name="arrival" var="arrival"/><br>
                </td>
            </tr>
            <tr>
                <td class="content"><fmt:message key="label.departure"/>:<span class="asterisk"> *</span></td>
                <td><input class="field" type="date" min="${tomorrow}" value="${tomorrow}"
                           required oninput="setCustomValidity('')"
                           oninvalid="this.setCustomValidity('<fmt:message key="label.fill"/>')"
                           name="departure"/><br>
                </td>
            </tr>
            <tr align="left !important">
                <td class="content"><fmt:message key="label.room"/>:<span class="asterisk"> *</span></td>
                <td>
                    <select class="field" name="room_type" required oninput="setCustomValidity('')"
                            oninvalid="this.setCustomValidity('<fmt:message key="label.fill"/>')">
                        <option value="male"><fmt:message key="label.male"/></option>
                        <option value="female"><fmt:message key="label.female"/></option>
                        <option value="mixed"><fmt:message key="label.mixed"/></option>
                    </select><br>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><br><input type="hidden" name="command" value="order"/>
                    <input class="button" type="submit" name="order" value=<fmt:message key="button.book"/>></td>
            </tr>
        </table>
    </form>
    <div class="warning">
        <br><br>
        ${errorApplication}
        <br/>
    </div>
</div>
<c:import url="/jsp/additional/footer.jsp"/>
</body>
</html>