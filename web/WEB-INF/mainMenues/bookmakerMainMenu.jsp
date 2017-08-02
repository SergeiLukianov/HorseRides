<%--
  Created by IntelliJ IDEA.
  User: Serj
  Date: 26.07.2017
  Time: 2:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница букмекера</title>
</head>
<body>

<table>
    <form action="/setCoefficient" method="get">
        <tr>
            <td>
                <input type="submit" value="УСТАНОВИТЬ КОЭФФИЦИЕНТЫ">
            </td>
        </tr>
    </form>
    <form action="/editCoefficient" method="get">
        <tr>
            <td>
                <input type="submit" value="ИЗМЕНИТЬ КОЭФФИЦИЕНТЫ">
            </td>
        </tr>
    </form>
</table>
<p><a href="/logout">Выйти из системы</a> </p>
</body>
</html>
