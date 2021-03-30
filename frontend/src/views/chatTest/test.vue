<template>
  <div id="app">
    <div>
      <input type="text" v-model="userName" class="mr-2" ref="user-name-input" :disabled="stompClient"/>
      <button @click="socketConnect" class="chat-room-enter-btn" :disabled="stompClient" v-if="!stompClient">채팅방 입장
      </button>
      <button @click="socketDisconnect" class="chat-room-enter-btn" :disabled="!stompClient" v-else>채팅방 나가기</button>
    </div>
    <div class="chat-room-wrap" v-if="stompClient">
      <input type="text" v-model="message" class="blocked mr-2" ref="user-name-input" @keyup.enter="sendMessage"
             placeholder="메세지를 입력하세요!"/>
    </div>
    <div>
      <p style="padding: 0; margin: 0" v-for="(obj, index) in receivedMessages" :key="index">
        {{ obj.sender }} : {{ obj.content }}
      </p>
    </div>
  </div>
</template>
<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  name: 'App',
  data() {
    return {
      userName: "",
      message: "",
      stompClient: null,
      receivedMessages: [],
    }
  },

  methods: {

    sendMessage() {
      if (this.message.trim() && this.stompClient) {
        const sendMessage = {
          sender: this.userName,
          content: this.message.trim(),
          type: 'CHAT'
        };
        this.stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(sendMessage));
        this.message = '';
      }
    },

    onMessageReceived(payload) {
      const receiveMessage = JSON.parse(payload.body);

      if (receiveMessage.type === 'JOIN') {
        receiveMessage.content = receiveMessage.sender + ' joined!';
      } else if (receiveMessage.type === 'LEAVE') {
        receiveMessage.content = receiveMessage.sender + ' left!';
      }

      this.receivedMessages.push(receiveMessage)

    },

    onConnected() {
      this.stompClient.subscribe('/topic/public', this.onMessageReceived);
      this.stompClient.send('/app/chat.addUser',
          {},
          JSON.stringify({content: '', sender: this.userName, type: 'JOIN'})
      );
    },

    onError(error) {
      console.log(error);
    },

    onDisconnected() {
      this.stompClient = null;
      this.receivedMessages = [];
    },

    socketConnect() {
      if (!this.userName.trim()) {
        alert('대화명을 입력해주세요!');
        this.$refs['user-name-input'].focus();
        return false;
      }

      const serverURL = "http://localhost:8080/ws"
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect('', this.onConnected, this.onError);
    },

    socketDisconnect() {
      this.stompClient.send("/app/chat.sendMessage", {}, JSON.stringify({
        content: '', sender: this.userName, type: 'LEAVE'
      }));
      this.stompClient.disconnect(this.onDisconnected);
    }
  }
}
</script>
<style>

.mr-1 {
  margin-right: 4px;
}

.mr-2 {
  margin-right: 8px;
}

.mr-3 {
  margin-right: 12px;
}

.blocked {
  display: block;
}

input[type=text] {
  display: inline-block;
  border: 1px solid #ced4da;
  border-radius: 4px;
  outline: none;
  padding: 10px;
  font-size: 14px;
}

input[type=text]:focus {
  border-color: #80bdff;
}

.chat-room-enter-btn {
  display: inline-block;
  color: #fff;
  background-color: #343a40;
  border-color: #343a40;
  border-radius: 4px;
  font-size: 14px;
  padding: 10px;
  cursor: pointer;
  appearance: none;
}

</style>
