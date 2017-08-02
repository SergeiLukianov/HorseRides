<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Serj
  Date: 27.07.2017
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменение коэффициентов</title>
</head>
<body>

<table border="1">
    <tr>
        <td>Дата</td>
        <td>Номер лошади</td>
        <td>Имя лошади</td>
        <td>Коэффициент</td>
    </tr>
    <form method="post" action="/editCoefficient">
        <c:forEach items="${coefficients}" var="coefficient">
            <tr>
                <td>${coefficient.date}</td>
                <td>${coefficient.horseId}</td>
                <td>${coefficient.horseName}</td>
                <td>${coefficient.coefficient}</td>
                <td><input required type="radio" name="coeff" value="${coefficient.id}"></td>
            </tr>
        </c:forEach>

        <p><input type="submit" value="Продолжить"></p>
    </form>
</table>

<p><a href="/mainmenu">На главную страницу</a></p>
<p><a href="/logout">Выйти из системы</a></p>
</body>
</html>
