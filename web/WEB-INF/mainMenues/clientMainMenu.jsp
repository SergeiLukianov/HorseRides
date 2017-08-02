<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Serj
  Date: 25.07.2017
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница клиента</title>
</head>
<body>
<table>

    <tr>
        <form action="/newbet" method="get">
            <td>
                <input type="submit" value="СДЕЛАТЬ СТАВКУ">
            </td>
        </form>
    </tr>
    <tr>
        <form action="/mybets" method="get">
            <td>
                <input name="myBetsButton" type="submit" value="МОИ СТАВКИ">
            </td>
        </form>
    </tr>
</table>

<p></p>
<p><a href="/logout">Выйти из системы</a> </p>
</body>
</html>
