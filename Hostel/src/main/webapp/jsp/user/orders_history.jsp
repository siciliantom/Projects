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
    <title>Orders_history</title>
    <link href="<c:url value="/css/main_view.css" />" rel="stylesheet"/>
</head>
<body>
<c:import url="/jsp/additional/welcome_header.jsp"/>
<div id="section">
    <form name="backForm" method="POST" action="controller">
        <input type="hidden" name="command" value="back"/>
        <input class="back_button" type="submit" value=<fmt:message key="label.back"/>>
    </form>

    <c:if test="${not empty historyApplicationListUser}">
        <h3><span class="price"><fmt:message key="label.your_history"/></span></h3>
        <br/>
        <table align="left">
            <tr class="content">
                <th><fmt:message key="label.places"/></th>
                <th><fmt:message key="label.arrival"/></th>
                <th><fmt:message key="label.departure"/></th>
                <th><fmt:message key="label.cost"/></th>
                <th><fmt:message key="label.room"/></th>
                <th><fmt:message key="label.confirmed"/></th>

            </tr>
            <form name="historyApplicationForm" method="POST" action="controller">
                <c:forEach var="application" items="${historyApplicationListUser}">
                    <tr>
                        <td>${application.placesAmount}</td>
                        <td>${application.arrivalDate}</td>
                        <td>${application.departureDate}</td>
                        <td>${application.finalPrice}$</td>
                        <td>${application.room.type}</td>
                        <td>${application.confirmed}</td>

                    </tr>
                </c:forEach>

            </form>
        </table>
    </c:if>
    <c:if test="${empty historyApplicationListUser}">
        <span class="price"><fmt:message key="label.no_history"/></span>
    </c:if>
</div>
<c:import url="/jsp/additional/footer.jsp"/>
</body>
</html>
