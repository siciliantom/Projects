<%--
  Created by IntelliJ IDEA.
  User: Kate
  Date: 19.01.2016
  Time: 18:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Choose parsing</title>
</head>
<body>
<div align="center">
<form name=choice method=post action = /chooseAction >
    <h2>Hello,<br>
        please select parsing type:</h2> <br>
    <select name="type">
        <option value="SAXParsing">SAXParsing</option>
        <option value="DOMParsing">DOMParsing</option>
        <option value="StAXParsing">StAXParsing</option>
    </select>
    <input type=submit value=submit>
</form>
</div>
</body>
</html>
