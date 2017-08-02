<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменение коэффициента</title>
</head>
<body>
<table border="1">
    <tr>
        <td>Дата</td>
        <td>Номер лошади</td>
        <td>Имя лошади</td>
        <td>Установленный коэффициент</td>
        <td>Новый коэффициент</td>
    </tr>
    <form action="/editCoefficientConfirmation" method="post">
        <tr>
            <td>${coefficientToEdit.date}</td>
            <td>${coefficientToEdit.horseId}</td>
            <td>${coefficientToEdit.horseName}</td>
            <td>${coefficientToEdit.coefficient}</td>
            <td><input name="newCoeff" type="text"></td>
            <td>
                <button name="button" value="${coefficientToEdit.id}">Подтвердить</button>
            </td>
        </tr>
    </form>
</table>
</body>
</html>
