<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<table>
    <tr>
        <th>Email</th>
        <th>Id</th>
    </tr>
    <#list users as user>
        <tr>
            <td>${user.email}</td>
            <td>${user.id}</td>
        </tr>
    </#list>
</table>

</body>
</html>
