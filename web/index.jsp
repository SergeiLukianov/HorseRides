<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>Вход в систему</title>
</head>
<body>
<p>
<a href="/registration">Регистрация</a>
</p>
<p>
    <a href="/logout">Выйти из системы</a>
</p>

<table align="center">
    <form action="/authorization" method="post">
        <div style="height:20%;"/>
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
</body>
</html>
