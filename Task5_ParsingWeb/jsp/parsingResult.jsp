<%--
  Created by IntelliJ IDEA.
  User: Kate
  Date: 24.01.2016
  Time: 8:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>DOM parsing result</title>
</head>
<body>
<br>
<h2 align="center" > ${res} parsing </h2>
    <br>
<h3 align="center">ArtCards</h3>
    <br>
    <table border="2" align="center">
        <tr>
        <th> Theme</th>
        <th> Country</th>
        <th> Year</th>
        <th> Valuable</th>
        <th> Type</th>
        <th> Posted</th>
        <th> Registry</th>
            <th> picture:<br>Name</th>
            <th> picture:<br>Author</th>
        </tr>
        <c:forEach var="card" items="${artCardList}">
        <tr>
            <td> ${card.theme} </td>
            <td> ${card.country} </td>
            <td> ${card.year} </td>
            <td> ${card.valuable} </td>
            <td> ${card.type} </td>
            <td> ${card.posted} </td>
            <td> ${card.registry} </td>
            <td> ${card.picture.name} </td>
            <td> ${card.picture.author} </td>
        </tr>
        </c:forEach>
    </table>
<br>
<h3 align="center">DocumentaryCards</h3>
<br>

<table border="2" align="center">
    <tr>
        <th> Theme</th>
        <th> Country</th>
        <th> Year</th>
        <th> Valuable</th>
        <th> Type</th>
        <th> Posted</th>
        <th> Registry</th>
        <th> photo:<br>Name</th>
        <th> photo:<br>Author</th>
        <th> photo: <br>Year-of-shot</th>
    </tr>
    <c:forEach var="card" items="${docCardList}">
        <tr>
            <td> ${card.theme} </td>
            <td> ${card.country} </td>
            <td> ${card.year} </td>
            <td> ${card.valuable} </td>
            <td> ${card.type} </td>
            <td> ${card.posted} </td>
            <td> ${card.registry} </td>
            <td> ${card.photo.name} </td>
            <td> ${card.photo.author} </td>
            <td> ${card.photo.yearOfShot} </td>
        </tr>
    </c:forEach>
</table>
<br>
<form method=post action=/chooseAction>
    <input type=submit value=back>
    <input type=hidden name=type value=returnBack>
</form>
</body>
</html>
