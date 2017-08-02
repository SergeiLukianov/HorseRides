<%--
  Created by IntelliJ IDEA.
  User: Serj
  Date: 26.07.2017
  Time: 2:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница админа</title>
</head>
<body>
<table>
    <form action="/makeapayment" method="get">
        <tr>
            <td>
                <input name="payABetButton" type="submit" value="Выплатить">
            </td>
        </tr>
    </form>
    <form action="/registerresult" method="get">
        <tr>
            <td>
                <input name="registrationButton" type="submit" value="Зарегистрировать результат">
            </td>
        </tr>
    </form>
</table>
<p><a href="/logout">Выйти из системы</a></p>
</body>
</html>
