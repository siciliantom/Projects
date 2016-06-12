<%--
  Created by IntelliJ IDEA.
  User: Kate
  Date: 26.03.2016
  Time: 7:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>All_clients</title>
    <link href="<c:url value="/css/main_view.css" />" rel="stylesheet"/>
</head>
<body>
<c:import url="/jsp/additional/welcome_header.jsp"/>
<div id="section">
    <form name="backForm" method="POST" action="controller">
        <input type="hidden" name="command" value="back">
        <input class="back_button" type="submit" value=<fmt:message key="label.back"/>>
    </form>
    <c:if test="${not empty clientsList}">
        <h3>
            <span class="price"><fmt:message key="label.clients"/></span>
        </h3>
        <table border="0" align="left">
            <tr class="content">
                <th></th>
                <th><fmt:message key="label.number"/></th>
                <th><fmt:message key="label.user_name"/></th>
                <th><fmt:message key="label.user_surname"/></th>
                <th><fmt:message key="label.user_country"/></th>
                <th><fmt:message key="label.ban"/></th>
            </tr>
            <form name="applicationForm" method="POST" action="controller">
                <c:forEach var="client" items="${clientsList}">
                    <tr>
                        <td><input type="radio" name="client_id" value="${client.id}"/></td>
                        <td> ${client.id} </td>
                        <td> ${client.name} </td>
                        <td> ${client.surname} </td>
                        <td> ${client.country} </td>
                        <td> ${client.status.banned} </td>
                    </tr>
                </c:forEach>

                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td><br>
                        <input type="hidden" name="command" value="ban"/>
                        <fmt:message key="button.set_ban" var="banButton"/>
                        <input class="button" type="submit" value="${banButton}"/>
                    </td>
                </tr>
            </form>
        </table>
    </c:if>
    <c:if test="${empty clientsList}">
        <span class="please"><fmt:message key="label.no_clients"/></span>
    </c:if>
    <br><br><br><br>
</div>
<c:import url="/jsp/additional/footer.jsp"/>
</body>
</html>
