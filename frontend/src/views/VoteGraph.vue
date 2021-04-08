<template>
  <div>
    <HNavGray />
    <div class="graph-container">
      <b-modal
        id="bv-modal-example2"
        hide-header-close
        hide-footer
        no-close-on-backdrop
      >
        <template #modal-title>LOGIN</template>
        <div style="text-align: center; font-family:NIXGONM-Vb;">
          로그인 후 투표결과를 확인할 수 있습니다.
          <img src="@/assets/vnc_logo.png" style="margin-top:-40px" />
        </div>
        <div
          class="d-block text-center justify-center"
          style="margin-top:-60px"
        >
          <kakaoLogin />
        </div>
      </b-modal>

      <div class="graph-content1">
        <strong>Reliable <br />Vote.</strong>
        <h3>투표결과를 확인하세요.</h3>
        <p
          id="votepage_desc"
          style="margin-left: 0px; margin-top: 0px;font-size: 24px;"
        >
          블록체인을 적용한 신뢰성있는 통계 시스템! <br />눈으로 직접 확인
          해보세요!
        </p>
      </div>
      <div class="graph-content2">
        <h1 id="votepage_title">{{ mainTitle }}</h1>
        <div>
          <b-badge variant="success" v-if="this.endDayCheck()">진행중</b-badge>
          <b-badge variant="secondary" v-else>마감</b-badge>
        </div>
        <div class="graph-content2-gul">
          <img
            v-if="mainImagePath != ''"
            :src="mainImagePath"
            alt=""
            style="width: 400px; height: 300px; border-radius: 20px;margin-top: 30px"
          />
          <p id="votepage_desc">
            {{ mainDescription }}
          </p>
          <hr style="width:80%" />
          <h1 id="votepage_title" style="font-size:30px">투표 결과</h1>
          <GChart type="BarChart" :data="chartData" :options="chartOptions" />
        </div>
        <!-- <p>메인내용</p> -->
        <!-- <b-button>참가자 목록</b-button> -->
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
          // stroke : '#666',
          // strokeWidth : 5,
        },
        // width: 1000,
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
      // setInterval(() => {
      //   this.chartData[1][1]++;
      //   console.log(this.chartData);
      // }, 1000);
    }
  },
  mounted() {
    if (this.isLogin == false) {
      this.$bvModal.show("bv-modal-example2");
    }
    console.log("Test");
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
      console.log(localStorage.getItem("access_token"));
      console.log(localStorage.getItem("myData"));
      if (
        localStorage.getItem("access_token") == undefined ||
        localStorage.getItem("myData") == undefined
      ) {
        console.log("로그인 안됨.");
      } else {
        console.log("로그인 됨.");
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
      console.log(rs);

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
        console.log(el.title + "데이터 확인" + el.count + "dd" + this.colorIdx);
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
      console.log("에러임");
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
      if (receiveMessage.sender % 6 == 0) {
        this.color = "#BCE55C"; // #BCE55C 연두
      } else if (receiveMessage.sender % 6 == 1) {
        this.color = "#A566FF"; // #A566FF 보라
      } else if (receiveMessage.sender % 6 == 2) {
        this.color = "#5CD1E5"; // #5CD1E5 하늘
      } else if (receiveMessage.sender % 6 == 3) {
        this.color = "#F361DC"; // #F361DC 분홍
      } else if (receiveMessage.sender % 6 == 4) {
        this.color = "#E5D85C"; // #E5D85C 노랑
      } else if (receiveMessage.sender % 6 == 5) {
        this.color = "#F15F5F"; // #F15F5F 빨강
      }
      this.chartData[parseInt(receiveMessage.sender) + 1][1]++;
      this.chartData[parseInt(receiveMessage.sender) + 1][2] = this.color;
      // jjh_test
      this.chartData = this.chartData.map((item, index) => {
        console.log(
          "index" + index + "sender" + parseInt(receiveMessage.sender)
        );
        if (index != parseInt(receiveMessage.sender) || index == 0) {
          return item;
        }
        return item.map((item, index) => {
          if (index !== 0 && index !== 2) {
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
  height: 100%;
  display: flex;
  justify-content: flex-end;
  background: #f9f9f9;
}
.graph-content1 strong {
  font-size: 115px;
  line-height: 1.25em;
  position: fixed;
}
.graph-content1 h3 {
  font-size: 33px;
  font-weight: 700;
  position: fixed;
}
.graph-content1 p {
  font-size: 20px;
  position: fixed;
}
.graph-content2 {
  text-align: center;
  width: 65%;
  background: #e9ecef;
  padding-top: 15px;
  padding-right: 20px;
  padding-left: 20px;
}
.graph-content2-gul {
  min-height: 50%;
}
</style>
