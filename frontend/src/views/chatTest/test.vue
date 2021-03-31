<template>
  <div>
    <div style="display : flex; margin-top : 10%; ">
      <input type="text" v-model="userName" class="mr-2" ref="user-name-input" :disabled="stompClient"/>
      <button @click="socketConnect" class="chat-room-enter-btn" :disabled="stompClient" v-if="!stompClient">채팅방 입장
      </button>
      <button @click="socketDisconnect" class="chat-room-enter-btn" :disabled="!stompClient" v-else>채팅방 나가기</button>
    </div>
    <div class="chat-room-wrap" v-if="stompClient"  style="display : flex; margin-top : 10%; ">
      <input type="text" v-model="message" class="blocked mr-2" ref="user-name-input" @keyup.enter="sendMessage"
             placeholder="메세지를 입력하세요!"/>
    </div>
    <div>
      <p style="padding: 0; margin: 0" v-for="(obj, index) in receivedMessages" :key="index">
        {{ obj.sender }} : {{ obj.content }}
      </p>
    </div>
        <div>
      <h1>데이터 샘플</h1>

       <GChart
    type="PieChart"
    :data="chartData"
    :options="chartOptions"
  />
    </div>
  </div>
</template>


<script>
// import Stomp from 'webstomp-client'
import {Stomp} from '@stomp/stompjs'
import SockJS from 'sockjs-client'
import { GChart } from 'vue-google-charts'

export default {
  // name: 'App',
  components:{
        GChart,
  },
  data() {
    return {
      userName: "",
      message: "",
      stompClient: null,
      receivedMessages: [],
            chartData: [
        ['Year', 'Sales', 'Expenses', 'Profit'],
        ['2014', 1000, 400, 200],
        ['2015', 1170, 460, 250],
        ['2016', 660, 1120, 300],
        ['2017', 1030, 540, 350]
      ],
      chartOptions: {
        chart: {
          title: 'Company Performance',
          subtitle: 'Sales, Expenses, and Profit: 2014-2017',
        }
      }
    }
  },
  created() {
  google.charts.load('current', {'packages':['corechart']});
  google.charts.setOnLoadCallback(this.drawChart());

  },
  methods: {
    drawChart() {
      var data = google.visualization.arrayToDataTable([
        ["Task", "Hours per Day"],
        ["Work", 9],
        ["Eat", 2],
        ["TV", 4],
        ["Gym", 2],
        ["Sleep", 8],
      ]);

      // Optional; add a title and set the width and height of the chart
      var options = { title: "My Average Day", width: 550, height: 400 };

      // Display the chart inside the <div> element with id="piechart"
      var chart = new google.visualization.PieChart(
        document.getElementById("piechart")
      );
      chart.draw(data, options);
    },



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
