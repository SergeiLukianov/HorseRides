<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход в систему</title>
</head>
<body>
<table>
    <form action="/authorization" method="post">
        <tr>
            <td>Имя пользователя:</td>
        </tr>
        <tr>
            <td>
                <input required autofocus width="50" name="userName" type="text">
            </td>
        </tr>
        <tr></tr>

        <tr>
            <td>Пароль:</td>
        </tr>
        <tr>
            <td>
                <input required width="50" name="password" type="password">
            </td>
        </tr>
        <tr align="right">
            <td></td>
            <td>
                <input name="entranceButton" type="submit" value="Вход">
            </td>
        </tr>
    </form>
</table>

<p>
    <a href="/registration">Регистрация</a>
</p>
<p>
    <a href="/logout">Выйти из системы</a>
</p>
</body>
</html>
