<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Serj
  Date: 27.07.2017
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Установка коэффициентов</title>
</head>
<body>
<p>
    <h5>Коэффициент не должен быть меньше 1.5. В качестве разделителя используйте точку*</h5>
    <table border="1">
        <tr>
            <td>Лошадь№</td>
            <td>Имя лошади</td>
            <td>Коэффициент*</td>
        </tr>
        <form action="/setCoefficient" method="post">
            <c:forEach items="${horses}" var="horse">
            <tr>
                <td>${horse.id}</td>
                <td>${horse.name}</td>
                <td><input required name="coefficient${horse.id}" type="text"></td>
            </tr>
            </c:forEach>
            <input required name="date" value="${sessionScope.currentDate}" min="${sessionScope.currentDate}"
                   type="date">
<p><input type="submit" value="Подтвердить"></p>
</form>
</table>

<p><a href="/mainmenu">На главную страницу</a> </p>
<p><a href="/logout">Выйти из системы</a></p>
</body>
</html>
