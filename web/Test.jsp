<%--
  Created by IntelliJ IDEA.
  User: Serj
  Date: 30.07.2017
  Time: 20:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Тест</title>
</head>
<body>
<form action="/test">
    <%if (request.getAttribute("att") == null){%>
    <input type="submit" value="Submit">
    <%} else {%>
    <input disabled type="submit" value="Submit">
    <%}%>
</form>
</body>
</html>
