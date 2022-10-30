var stompClient = null;

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  if (connected) {
    $("#conversation").show();
  } else {
    $("#conversation").hide();
  }
}

function connect(id, currentUserId) {
  var socket = new SockJS('/gs-guide-websocket');
  stompClient = Stomp.over(socket);
  stompClient.debug = null;

  stompClient.connect({}, function (frame) {
    setConnected(true);
    stompClient.subscribe('/topic/greetings/' + id, function (greeting) {
      const body = JSON.parse(greeting.body);

      let msgHistory = document.querySelector('.msg_history');

      if (body.memberId === currentUserId) {
        msgHistory.append(getOugGoingMsg(body.message, body.createdDate));
      } else {
        msgHistory.append(getIncomingMsg(body.message, body.createdDate));
      }


      scroll();
    });
  });

  scroll();
}

function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  setConnected(false);
}

function sendName(id, message) {
  stompClient.send(
    "/app/hello/" + id,
    {},
    JSON.stringify({'message': message})
  );
}

function getOugGoingMsg(message, createdDate) {
  const div = document.createElement('div');
  div.innerHTML = `
    <div class="outgoing_msg">
      <div class="sent_msg">
        <p>${message}</p>
        <span class="time_date">${createdDate}</span>
      </div>
    </div>
  `;
  return div;
}

function getIncomingMsg(message, createdDate) {
  const div = document.createElement('div');
  div.innerHTML = `
    <div class="incoming_msg">
        <div class="incoming_msg_img"><img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"></div>
        <div class="received_msg">
            <div class="received_withd_msg">
                <p>${message}</p>
                <span class="time_date">${createdDate}</span>
            </div>
        </div>
    </div>
  `;
  return div;
}

function scroll() {
  let msgHistory = document.querySelector('.msg_history');
  msgHistory.scrollTop = msgHistory.scrollHeight;
}