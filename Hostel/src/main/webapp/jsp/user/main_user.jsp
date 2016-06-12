<%--
  Created by IntelliJ IDEA.
  User: Kate
  Date: 23.02.2016
  Time: 3:34
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
    <title>Home_user</title>
    <link href="<c:url value="/css/main_view.css" />" rel="stylesheet"/>
</head>
<body>
<c:import url="/jsp/additional/welcome_header.jsp"/>
<div id="section">
    <c:if test="${not empty applicationListUser}">
        <h3><span class="price"><fmt:message key="label.your_orders"/></span></h3>
        <br/>
        <table align="left">
            <tr class="content">
                <th></th>
                <th><fmt:message key="label.places"/></th>
                <th><fmt:message key="label.arrival"/></th>
                <th><fmt:message key="label.departure"/></th>
                <th><fmt:message key="label.cost"/></th>
                <th><fmt:message key="label.room"/></th>
                <th><fmt:message key="label.confirmed"/></th>
            </tr>
            <form name="applicationForm" method="POST" action="controller">
                <c:forEach var="application" items="${applicationListUser}">
                    <tr>
                        <td><input type="checkbox" name="rowid" value="${application.id}"></td>
                        <td>${application.placesAmount}</td>
                        <td>${application.arrivalDate}</td>
                        <td>${application.departureDate}</td>
                        <td>${application.finalPrice}$</td>
                        <td>${application.room.type}</td>
                        <td>${application.confirmed}</td>

                    </tr>
                </c:forEach>
                <c:forEach var="application" items="${frozenApplications}">
                    <tr bgcolor=#e9967a>
                        <td></td>
                        <td>${application.placesAmount}</td>
                        <td>${application.arrivalDate}</td>
                        <td>${application.departureDate}</td>
                        <td>${application.finalPrice}$</td>
                        <td>${application.room.type}</td>
                        <td>${application.confirmed}</td>
                        <td><fmt:message key="label.freeze"/></td>
                    </tr>
                </c:forEach>
                <tr></tr>
                <tr>
                    <td></td>

                    <td>
                        <input type="hidden" name="command" value="delete_application"/>
                        <fmt:message key="button.cancel" var="cancelButton"/>
                        <input class="button" type="submit" value="${cancelButton}"/>
                    </td>
            </form>
            <td></td>
            <td></td>
            <td></td>
            <td>
                <br>

                <form name="show OrderForm" method="POST" action="controller">
                    <input type="hidden" name="command" value="show_order_form"/>
                    <fmt:message key="button.order" var="orderButton"/>
                    <input class="button" type="submit" value="${orderButton}"/>
                </form>
            </td>
            <td>
                <br>

                <form name="applicationForm" method="POST" action="controller">
                    <input type="hidden" name="command" value="show_history"/>
                    <fmt:message key="button.history" var="historyButton"/>
                    <input class="button" type="submit" value="${historyButton}"/>
                </form>

            </td>
            </tr>
        </table>
    </c:if>
    <c:if test="${empty applicationListUser}">
        <span class="please"><fmt:message key="label.no_applications"/></span><br><br>
        <table align="center">
            <tr>
                <td>
                    <form name="applicationForm" method="POST" action="controller">
                        <input type="hidden" name="command" value="show_order_form"/>
                        <fmt:message key="button.order" var="orderButton"/>
                        <input class="button" type="submit" value="${orderButton}"/>
                    </form>
                </td>
                <td></td>
                <td>
                    <form name="historyForm" method="POST" action="controller">
                        <input type="hidden" name="command" value="show_history"/>
                        <fmt:message key="button.history" var="historyButton"/>
                        <input class="button" type="submit" value="${historyButton}"/>
                    </form>
                </td>
            </tr>

        </table>
    </c:if>
    <div class="warning">
        <br><br>
        ${accessPermissionError}
        <br/>
    </div>
</div>
<c:import url="/jsp/additional/footer.jsp"/>
</body>
</html>
