<!doctype html>
<html lang="ru">
<#import "spring.ftl" as spring/>
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script src="static/js/chat.js"></script>
</head>
<div>
    <a href="?locale=ru"><@spring.message "sidebar.ru"/></a>
</div>
<div>
    <a href="?locale=us"><@spring.message "sidebar.en"/></a>
</div>  
<body onload="sendMessage('Login')">
<div>
    <input id="message" placeholder=<@spring.message "chat.urmessage"/>>
    <button onclick="sendMessage(
            $('#message').val())"><@spring.message "chat.sendmessage"/></button>
</div>
<div>
    <ul id="messages">

    </ul>
</div>
</body>
</html>