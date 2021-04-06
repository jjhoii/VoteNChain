<template>
  <div>
    <HNavGray />
    <div class="graph-container">
      <div class="graph-content1">
        <strong
          >Reliable <br />
          Vote.</strong
        >
        <h3>신뢰성있는 투표일까?</h3>
        <p>
          블록체인을 적용한 신뢰성있는 통계 시스템! <br />눈으로 직접
          경험해보세요!
        </p>
      </div>
      <div class="graph-content2">
        <h1>{{ mainTitle }}</h1>
        <!-- <h1>메인 제목</h1> -->
        <img
          v-if="mainImagePath != ''"
          :src="mainImagePath"
          alt=""
          style="width: 50%; height: 30%"
        />
        <p>
          {{ mainDescription }}
        </p>
        <!-- <p>메인내용</p> -->
        <b-button>참가자 목록</b-button>
        <GChart type="BarChart" :data="chartData" :options="chartOptions" />
      </div>
    </div>
  </div>
</template>

<script>
import HNavGray from '@/components/common/HNavGray';
import { GChart } from 'vue-google-charts';
import { Utils } from '@/utils/index.js';
import axios from 'axios';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  components: {
    GChart,
    HNavGray,
    axios,
  },
  data() {
    return {
      mainTitle: '',
      mainDescription: '',
      mainImagePath: '',
      imageExist: false,
      // Array will be automatically processed with visualization.arrayToDataTable function
      loaded: false,
      chartData: [
        ['Element', 'Density', { role: 'style' }, { role: 'annotation' }],
        ['Copper', 8.94, '#b87333', 1],
        ['Silver', 10.49, 'silver', 'Ag'],
        ['Gold', 19.3, 'gold', 'Au'],
        ['Platinum', 21.45, 'color: #e5e4e2', 'Pt'],
      ],
      chartOptions: {
        chart: {
          title: 'Company Performance',
          subtitle: 'Sales, Expenses, and Profit: 2014-2017',
        },
      },
    };
  },
  async created() {
    // 오류 발생 임시 주석 처리
    this.test();
    await this.getContractAddress();
    // setInterval(() => {
    //   this.chartData[1][1]++;
    //   console.log(this.chartData);
    // }, 1000);
  },
  methods: {
    async getContractAddress() {
      // console.log("true");
      this.$store.state.loading.text = '투표 주소를 가져오는 중입니다...';
      this.$store.state.loading.enabled = true;
      try {
        const res = await axios.get(`${SERVER_URL}/vote/read`, {
          params: { hashKey: this.$route.params.hashKey },
        });
        const idx = res.data.vote.contractAddress * 1;
        await this.getData(idx);

        this.n = idx;
      } catch (err) {
        console.log(err);
      }
      this.$store.state.loading.enabled = false;
    },

    async getData(idx) {
      // get vote data
      this.$store.state.loading.text = '투표 데이터를 가져오는 중입니다...';
      this.$store.state.loading.enabled = true;
      const rs = await Utils.call(Utils.contract.methods.getVote, [idx]);
      console.log(rs);

      // set data
      this.mainTitle = rs.title;
      this.mainDescription = rs.description;
      this.mainImagePath = rs.imagePath;
      this.imageExist = rs.bImageExist;
      // set chart
      this.chartData = [['Key', 'Value']];
      rs.items.forEach((el) => {
        console.log(el.title + '데이터 확인' + el.count);
        this.chartData.push([el.title, el.count * 1]);
      });

      // load complete
      this.loaded = true;
      this.$store.state.loading.enabled = false;
    },
    drawChart() {
      var data = google.visualization.arrayToDataTable([
        ['Task', 'Hours per Day'],
        ['Work', 9],
        ['Eat', 2],
        ['TV', 4],
        ['Gym', 2],
        ['Sleep', 8],
      ]);

      // Optional; add a title and set the width and height of the chart
      var options = { title: 'My Average Day', width: 550, height: 400 };

      // Display the chart inside the <div> element with id="piechart"
      var chart = new google.visualization.PieChart(
        document.getElementById('piechart')
      );
      chart.draw(data, options);
    },
    async getContractAddress() {
      try {
        const res = await axios.get(`${SERVER_URL}/vote/read`, {
          params: { hashKey: this.$route.params.hashKey },
        });
        const idx = res.data.vote.contractAddress * 1;
        await this.getData(idx);
        this.n = idx;
      } catch (err) {
        console.log(err);
      }
    },

    test() {
      const serverURL = 'http://localhost:8080/ws';
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect('', this.onConnected, this.onError);
    },

    onConnected() {
      //sendData
      var hashcode = this.$route.params.hashKey;
      this.stompClient.subscribe(
        '/socket/chart/' + hashcode + '/send',
        this.onMessageReceived
      );
    },
    onError(error) {
      console.log('에러임');
      console.log(error);
    },
    onDisconnected() {
      this.stompClient = null;
      this.receivedMessages = [];
    },

    onMessageReceived(payload) {
      const receiveMessage = JSON.parse(payload.body);
      console.log(receiveMessage.sender); //얘는 인덱스 값
      // this.chartData = [['Key', 'Value']];
      console.log(this.chartData);

      this.chartData[parseInt(receiveMessage.sender) + 1][1]++;
      // this.chartData.push([receiveMessage.sender, count + 1]);
    },
  },
};
</script>

<style>
.graph-container {
  padding-top: 100px;
  padding-right: 3rem;
  padding-left: 3rem;
  height: 100vh;
  display: flex;
  justify-content: flex-end;
  background: #f9f9f9;
  position: relative;
}

.graph-content1 strong {
  position: absolute;
  font-size: 120px;
  left: 10%;
  top: 10%;
  line-height: 1.25em;
}
.graph-content1 h3 {
  position: absolute;
  font-size: 30px;
  left: 10%;
  top: 55%;
  font-weight: 700;
}
.graph-content1 p {
  position: absolute;
  font-size: 20px;
  left: 10%;
  top: 65%;
  z-index: 99;
}
.graph-content2 {
  text-align: center;
  width: 65%;
  background: #e9ecef;
}
</style>
