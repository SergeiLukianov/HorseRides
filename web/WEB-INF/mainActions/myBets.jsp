<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Мои ставки</title>
</head>
<body>
<table border="1">
    <tr>
        <td>Дата</td>
        <td>Лошадь№</td>
        <td>Сумма</td>
        <td>Коэффициент</td>
        <td>Состояние ставки</td>
        <td>Код</td>
    </tr>
    <c:forEach items="${myBets}" var="myBet" >
        <tr>
            <td>${myBet.date}</td>
            <td>${myBet.guess}</td>
            <td>${myBet.amount}</td>
            <td>${myBet.coefficient}</td>
            <td>${myBet.betState}</td>
            <td>${myBet.checkSum}</td>
        </tr>
    </c:forEach>
</table>
<p><a href="/mainmenu">На главную страницу</a> </p>
<p><a href="/logout">Выйти из системы</a></p>
</body>
</html>
