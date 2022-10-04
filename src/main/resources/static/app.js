var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect(id) {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings/' + id, function (greeting) {
            showGreeting(JSON.parse(greeting.body));
        });
        sendName(id, "님이 접속했습니다.");
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName(id, message) {
    stompClient.send(
        "/app/hello/" + id,
        {},
        JSON.stringify({'message': message})
    );
}

function showGreeting({ nickName, message }) {
    $("#greetings").append("<tr><td style='width: 100px;'>" + nickName + "</td><td>" + message + "</td></tr>");
}
