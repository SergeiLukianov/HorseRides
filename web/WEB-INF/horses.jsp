<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Лошади</title>
</head>
<body>
<table border="1">
    <c:forEach items="${horses}" var="horse">
        <tr>
            <td>${horse.id}</td>
            <td>${horse.name}</td>
        </tr>
    </c:forEach>
</table>
<p><a href="/mainmenu">На главную страницу</a> </p>
<p><a href="/logout">Выйти из системы</a></p>
</body>
</html>
