<!doctype html>
<html lang="ru">
<#import "spring.ftl" as spring/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign In ! </title>
</head>
<body>
<div>
    <a href="?locale=ru"><@spring.message "sidebar.ru"/></a>
</div>
<div>
    <a href="?locale=us"><@spring.message "sidebar.en"/></a>
</div>
<h1>Sign In !</h1>
<div>
    <form action="/signIn" method="post">
        <@spring.message "reg.email"/> <br>
        <input name="email">
        <@spring.message "reg.password"/> <br>
        <input type="password" name="password" placeholder="password">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <label>
            <input type="checkbox" name="remember-me"><@spring.message "reg.remember"/>
        </label>

        <input type="submit" value="Submit">
    </form>
</div>
</body>
</html>
