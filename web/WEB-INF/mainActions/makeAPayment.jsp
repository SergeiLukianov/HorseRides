<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Выплата</title>
</head>
<body>
<table border="1">
    <tr>
        <td>Клиент</td>
        <td>Дата</td>
        <td>Лошадь№</td>
        <td>Сумма</td>
        <td>Коэффициент</td>
        <td>Состояние</td>
    </tr>
    <form action="/makeapayment" method="post">
        <c:forEach items="${bets}" var="bet">

            <tr>
                <td>${bet.userName}</td>
                <td>${bet.date}</td>
                <td>${bet.guess}</td>
                <td>${bet.amount}</td>
                <td>${bet.coefficient}</td>
                <td>${bet.betState}</td>
                <td><input type="radio" name="betId" value="${bet.id}"></td>
            </tr>

        </c:forEach>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td><button name="button">Выплатить</button></td>
        </tr>
    </form>
</table>
<p><a href="/mainmenu">На главную страницу</a></p>
<p><a href="/logout">Выйти из системы</a></p>
</body>
</html>
