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
        <td>Код</td>
    </tr>
        <form action="/makeapayment" method="get">
            <tr>
                <td>${bet.userName}</td>
                <td>${bet.date}</td>
                <td>${bet.guess}</td>
                <td>${bet.amount}</td>
                <td>${bet.coefficient}</td>
                <td>${bet.betState}</td>
                <td><input name="checkSum" type="text"></td>
                <td><button name="button"  value="${bet.id}">Выплатить</button></td>
            </tr>
        </form>
</table>
<p><a href="/mainmenu">На главную страницу</a> </p>
<p><a href="/logout">Выйти из системы</a></p>
</body>
</html>
