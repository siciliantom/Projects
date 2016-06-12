<%--
  Created by IntelliJ IDEA.
  User: Kate
  Date: 07.04.2016
  Time: 13:31
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
    <title>Room choice</title>
    <link href="<c:url value="/css/main_view.css" />" rel="stylesheet"/>
</head>
<body>
<c:import url="/jsp/additional/welcome_header.jsp"/>
<div id="section">
    <form name="backForm" method="POST" action="controller">
        <input type="hidden" name="command" value="back"/>
        <input class="back_button" type="submit" value=<fmt:message key="label.back"/>>
    </form>
    <c:if test="${not empty appropriateRoomList}">
        <h3>
            <span class="price"><fmt:message key="label.available_rooms"/></span>
        </h3>
        <table border="0" align="left">
            <tr class="content">
                <th></th>
                <th><fmt:message key="label.number"/></th>
                <th><fmt:message key="label.places"/></th>
                <th><fmt:message key="label.places_left"/></th>
                <th><fmt:message key="label.room_price"/></th>
                <th><fmt:message key="label.room"/></th>
            </tr>
            <form name="confirmForm" method="POST" action="controller">
                <c:forEach var="room" items="${appropriateRoomList}">
                    <tr>
                        <td><input type="radio" name="room_id" value="${room.id}"></td>
                        <td> ${room.id}</td>
                        <td> ${room.maxPlaces} </td>
                        <td> ${room.freePlaces} </td>
                        <td> ${room.price}$</td>
                        <td> ${room.type} </td>
                    </tr>
                </c:forEach>

                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td><br>
                        <input type="hidden" name="command" value="confirm"/>
                        <fmt:message key="button.confirm" var="chooseButton"/>
                        <input class="button" type="submit" name="choose" value="${chooseButton}"/>
                    </td>
                </tr>
            </form>

        </table>
    </c:if>
    <c:if test="${empty appropriateRoomList}">
        <span class="please"><fmt:message key="label.no_rooms"/></span>
        <br>
        <br>

    </c:if>
    <br><br><br><br>
</div>
<c:import url="/jsp/additional/footer.jsp"/>
</body>
</html>
