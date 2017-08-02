<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Новая ставка</title>
</head>
<body>
<table border="1">
    <tr>
        <td>Дата</td>
        <td>Лошадь</td>
        <td>Сумма</td>
    </tr>
    <form action="/newbet" method="post">
        <tr>
            <td>
                <input required name="date" type="date" min="${sessionScope.currentDate}"
                       value="${sessionScope.currentDate}">
            </td>
            <td>
                <select required name="guess">
                    <c:forEach items="${horses}" var="horse">
                    <option value="${horse.id}">${horse.name}</option>
                    </c:forEach>
                </select>
            </td>
            <td><input required name="amount" type="number"></td>
            <td><input type="submit" value="Продолжить"></td>
        </tr>
    </form>
</table>

<p>
<h2>Коэффициенты</h2></p>
<table border="1">
    <tr>
        <td>Дата(гггг-мм-дд)</td>
        <td>Номер лошади</td>
        <td>Имя лошади</td>
        <td>Коэффициент</td>
    </tr>
    <c:forEach items="${requestScope.coefficients}" var="coeff">
        <tr>
            <td>${coeff.date}</td>
            <td>${coeff.horseId}</td>
            <td>${coeff.horseName}</td>
            <td>${coeff.coefficient}</td>
        </tr>
    </c:forEach>
</table>
<p><a href="/mainmenu">На главную страницу</a> </p>
<p><a href="/logout">Выйти из системы</a></p>
</body>
</html>
