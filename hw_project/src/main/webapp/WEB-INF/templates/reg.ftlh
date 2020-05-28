<!doctype html>
<html lang="en">
<#import "spring.ftl" as spring/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Reg</title>
</head>
<body>
<div>
    <a href="?locale=ru"><@spring.message "sidebar.ru"/></a>
</div>
<div>
    <a href="?locale=us"><@spring.message "sidebar.en"/></a>
</div>
<@spring.bind "signUpDto"/>
<form method="post" action="/signUp">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <@spring.message "reg.email"/> <br>
    <@spring.formInput "signUpDto.email"/>
    <@spring.showErrors "<br>"/>
    <@spring.message "reg.password"/>
    <@spring.formPasswordInput "signUpDto.password"/>
    <@spring.showErrors "<br>","error"/>
    <br>
    <input type="submit">
</form>
</body>
</html>