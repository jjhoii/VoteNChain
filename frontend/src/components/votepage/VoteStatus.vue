<template>
  <div>
    <div class="graph-container" style="padding-top: 30px;padding-bottom: 30px">
      <div
        class="graph-content2"
        style="text-align: center;width: 100%;background: #e9ecef;padding-top: 15px;"
      >
        <h1 id="votepage_title" style="font-size:60px;margin-bottom:20px">
          투표 현황
        </h1>
        <div>
          <b-badge variant="success" v-if="this.endDayCheck()">진행중</b-badge>
          <b-badge variant="secondary" v-else>마감</b-badge>
        </div>
        <div class="graph-content2-gul">
          <GChart type="BarChart" :data="chartData" :options="chartOptions" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HNavGray from "@/components/common/HNavGray";
import { GChart } from "vue-google-charts";
import { Utils } from "@/utils/index.js";
import axios from "axios";
import { Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import kakaoLogin from "@/components/socialLogin/kakao.vue";
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  components: {
    GChart,
    HNavGray,
    axios,
    kakaoLogin,
  },
  data() {
    return {
      mainTitle: "",
      mainDescription: "",
      mainImagePath: "",
      imageExist: false,
      colorIdx: 0,
      color: "",
      isLogin: false,
      endedAt: "",
      // Array will be automatically processed with visualization.arrayToDataTable function
      loaded: false,
      chartData: [
        [
          "Element",
          "Density",
          { role: "style" },
          { role: "annotation" },
        ] /* 3번째옵션이 style 4번째옵션이 그래프위숫자표시 */,
        ["Copper", 20, "#b87333", 1],
        ["Silver", 1, "silver", "Ag"],
        ["Gold", 1, "gold", "Au"],
        ["Platinum", 1, "color: #e5e4e2", "Pt"],
      ],
      chartOptions: {
        chart: {
          title: "Company Performance",
          subtitle: "Sales, Expenses, and Profit: 2014-2017",
        },
        /*  그래프 총넓이 */
        backgroundColor: {
          fill: "#e9ecef",
        },
        width: 1000,
        height: 400,
        legend: {
          position: "none",
          maxLines: 4,
        } /* 항목 위치 및 한줄에 최대4개  position은 top, 기본, none 조정가능 */,
        bar: { groupWidth: "75%" } /* 막대그래프 넓이 */,
        isStacked: true,
        hAxis: {
          minValue: 0,
          format: "0" /* 드디어찾았다... 소수점제거... */,
          gridlines: { count: 4 } /* 굵은 그리드 라인개수 */,
        },
      },
    };
  },
  async created() {
    this.loginCheck();
    // 오류 발생 임시 주석 처리
    if (this.isLogin == true) {
      await this.getContractAddress();
      this.subscribe();
    }
  },
  mounted() {
    if (this.isLogin == false) {
      this.$bvModal.show("bv-modal-example2");
    }
  },
  methods: {
    endDayCheck() {
      //지났음
      if (this.endedAt * 1000 < Date.now() * 1) {
        return false;
      }
      //안지났음
      return true;
    },
    loginCheck() {
      if (
        localStorage.getItem("access_token") == undefined ||
        localStorage.getItem("myData") == undefined
      ) {
      } else {
        this.isLogin = true;
      }
    },
    async getContractAddress() {
      // console.log("true");
      this.$store.state.loading.text = "투표 주소를 가져오는 중입니다...";
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
      this.$store.state.loading.text = "투표 데이터를 가져오는 중입니다...";
      this.$store.state.loading.enabled = true;
      const rs = await Utils.call(Utils.contract.methods.getVote, [idx]);

      // set data
      this.mainTitle = rs.title;
      this.mainDescription = rs.description;
      this.mainImagePath = rs.imagePath;
      this.imageExist = rs.bImageExist;
      this.endedAt = rs.endedAt;
      // set chart
      this.chartData = [["Key", "득표 수", { role: "style" }]];
      // var idx = 0;
      rs.items.forEach((el) => {
        //var color = '';
        if (this.colorIdx % 6 == 0) {
          this.color = "#BCE55C"; // #BCE55C 연두
        } else if (this.colorIdx % 6 == 1) {
          this.color = "#A566FF"; // #A566FF 보라
        } else if (this.colorIdx % 6 == 2) {
          this.color = "#5CD1E5"; // #5CD1E5 하늘
        } else if (this.colorIdx % 6 == 3) {
          this.color = "#F361DC"; // #F361DC 분홍
        } else if (this.colorIdx % 6 == 4) {
          this.color = "#E5D85C"; // #E5D85C 노랑
        } else if (this.colorIdx % 6 == 5) {
          this.color = "#F15F5F"; // #F15F5F 빨강
        }

        this.chartData.push([el.title, el.count * 1, this.color]);
        this.colorIdx++;
      });

      // load complete
      this.loaded = true;
      this.$store.state.loading.enabled = false;
    },
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
    subscribe() {
      const serverURL = `${SERVER_URL}/ws`;
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect("", this.onConnected, this.onError);
    },

    onConnected() {
      //sendData
      var hashcode = this.$route.params.hashKey;
      this.stompClient.subscribe(
        "/socket/chart/" + hashcode + "/send",
        this.onMessageReceived
      );
    },
    onError(error) {
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
      // jjh_test
      this.chartData = this.chartData.map((item, index) => {
        if (index != parseInt(receiveMessage.sender) || index == 0) {
          return item;
        }
        return item.map((item, index) => {
          if (index !== 0) {
            return item++;
          }
          return item;
        });
      });
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
  width: 80%;
  background: #e9ecef;
}
</style>
