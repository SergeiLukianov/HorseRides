<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация клиента</title>
</head>
<body>
<table>
    <form action="/registration" method="post">
        <tr>
            <td>Имя пользователя:</td>
        </tr>
        <tr>
            <td>
                <input required name="userName" type="text">
            </td>
        </tr>
        <tr>
            <td>
                Пароль:
            </td>
        </tr>
        <tr>
            <td>
                <input required name="password" type="password">
            </td>
        </tr>
        <tr>
            <td>
                Подтвердить пароль:
            </td>
        </tr>
        <tr>
            <td>
                <input required name="passwordConfirmation" type="password">
            </td>
        </tr>
        <tr align="right">
            <td>
                <select name="role">
                    <option disabled value="default">--Вы кто?--</option>
                    <option selected value="Client">Клиент</option>
                    <option value="Administrator">Администратор</option>
                    <option value="Bookmaker">Букмекер</option>
                </select>
            </td>
        </tr>
        <tr align="right">
            <td>
                <input name="registrationConfirmButton" type="submit" value="Подтвердить">
            </td>
        </tr>
    </form>
</table>

<p>
    <a href="/authorization">Вход</a>
</p>
<p>
    <a href="/logout">Выйти из системы</a>
</p>
</body>
</html>
