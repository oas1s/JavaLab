<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Чат</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
    <script>
        var webSocket;
        function connect() {
            webSocket = new WebSocket('ws://localhost:8090/chat');

            var email = getCookie(document.cookie);
            webSocket.onmessage = function receiveMessage(response) {
                let data = response['data'];
                let json = JSON.parse(data);
                $('#messagesList').first().after("<li>" + json['from'] + ' ' + json['text'] + "</li>")
            }
        }

        function sendMessage(text, pageId, chatId) {
            let message = {
                "text": text,
                "from": pageId,
                "to": chatId
            };
            webSocket.send(JSON.stringify(message));
        }

        function getCookie(cname) {
            var name = cname + "=";
            var decodedCookie = decodeURIComponent(document.cookie);
            var ca = decodedCookie.split(';');
            for(var i = 0; i <ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) == ' ') {
                    c = c.substring(1);
                }
                if (c.indexOf(name) == 0) {
                    return c.substring(name.length, c.length);
                }
            }
            return "";
        }
    </script>
</head>
<body onload="connect()">
<a href="http://localhost:8090/chatPage?id=1"><p>Комната1</p></a>
<a href="http://localhost:8090/chatPage?id=2"><p>Комната2</p></a>
<a href="http://localhost:8090/chatPage?id=3"><p>Комната3</p></a>
<div>
    <label for="message">Text of message</label>
    <input name="message" id="message" placeholder="Message">
    <button onclick="sendMessage($('#message').val(), '${pageId}', '${chatId}')">Send</button>
    <h3>Messages</h3>
    <ul id="messagesList">

    </ul>
</div>
</body>
</html>