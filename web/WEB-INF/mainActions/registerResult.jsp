<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Serj
  Date: 28.07.2017
  Time: 1:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация результата</title>
</head>
<body>
<table border="1">
    <tr>
        <td>Дата</td>
        <td>Имя лошади</td>
    </tr>
    <tr>
        <form action="/registerresult" method="post">
            <td>
                <input name="date" type="date" min="${sessionScope.currentDate}" max="${sessionScope.currentDate}" value="${sessionScope.currentDate}">
            </td>

            <td><select name="winner">
                <c:forEach items="${horses}" var="horse">
                    <option value="${horse.id}">${horse.name}</option>
                </c:forEach>
            </select></td>
            <td><input type="submit" value="Подтвердить"></td>
        </form>
    </tr>
</table>
<p><a href="/mainmenu">На главную страницу</a></p>
<p><a href="/logout">Выйти из системы</a></p>
</body>
</html>
