function sendMessage(text) {
    let body = {
        text: text
    };

    $.ajax({
        url: "/messages",
        method: "POST",
        data: JSON.stringify(body),
        contentType: "application/json",
        dataType: "json",
        complete: function () {
            if (text === 'Login') {
                receiveMessage();
            }
        }
    });
}

// LONG POLLING
function receiveMessage() {
    $.ajax({
        url: "/messages",
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (response) {
            console.log(response);
            response.forEach(resp => $('#messages').first().after('<li>' + resp['text'] + '</li>'));
            // $('#messages').first().after('<li>' + response[0]['text'] + '</li>');
            receiveMessage();
        }
    })
}