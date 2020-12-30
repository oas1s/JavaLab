<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>RegUser</title>
</head>
<body>
    <form action="/reguser" method="post">
        <input type="text" placeholder="Имя" name="name">
        <input type="text" placeholder="Фамилия" name="surname">
        <input type="text" placeholder="Mail" name="mail">
        <input type="text" placeholder="Количество лет" name="age">
        <input type="text" placeholder="Номер паспорта" name="passport">
        <input type="submit" placeholder="Отправить">
    </form>
</body>
</html>