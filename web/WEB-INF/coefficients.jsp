<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Коэффициенты</title>
</head>
<body>
<table border="1">
    <c:forEach items="${coefficients}" var="coeff">
        <td>${coeff.id}</td>
        <td>${coeff.date}</td>
        <td>${coeff.horseId}</td>
        <td>${coeff.coefficient}</td>
    </c:forEach>
</table>
<p><a href="/mainmenu">На главную страницу</a> </p>
<p><a href="/logout">Выйти из системы</a> </p>
</body>
</html>
