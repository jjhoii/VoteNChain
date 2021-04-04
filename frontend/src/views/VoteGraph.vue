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
        <img :src="mainImagePath" alt="" style="width: 50%; height: 30%" />
        <p>
          {{ mainDescription }}
        </p>
        <!-- <p>메인내용</p> -->
        <b-button>참가자 목록</b-button>
        <GChart type="ColumnChart" :data="chartData" :options="chartOptions" />
      </div>
    </div>
  </div>
</template>

<script>
import HNavGray from "@/components/common/HNavGray";
import { GChart } from "vue-google-charts";
import { Utils } from "@/utils/index.js";
import axios from "axios";
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  components: {
    GChart,
    HNavGray,
  },
  data() {
    return {
      mainTitle: "",
      mainDescription: "",
      mainImagePath: "",
      imageExist: false,
      // Array will be automatically processed with visualization.arrayToDataTable function
      loaded: false,
      chartData: [
        ["Year", "Sales", "Expenses", "Profit"],
        ["2014", 1000, 500, 200],
        ["2015", 1170, 460, 250],
        ["2016", 660, 1120, 300],
        ["2017", 1030, 540, 350],
      ],
      chartOptions: {
        chart: {
          title: "Company Performance",
          subtitle: "Sales, Expenses, and Profit: 2014-2017",
        },
      },
    };
  },
  async created() {
    await this.getContractAddress();
    // 오류 발생 임시 주석 처리
    // google.charts.load("current", { packages: ["corechart"] });
    // google.charts.setOnLoadCallback(this.drawChart());
    // this.getData(113);
  },
  methods: {
    async getData(idx) {
      // get vote data
      this.$store.state.loading.enabled = true;
      const rs = await Utils.call(Utils.contract.methods.getVote, [idx]);
      console.log(rs);

      // set data
      this.mainTitle = rs.title;
      this.mainDescription = rs.description;
      this.mainImagePath = rs.imagePath;
      this.imageExist = rs.bImageExist;
      // set chart
      // this.chartData = [["Key", "Value"]];
      rs.items.forEach((el) => {
        // this.chartData.push([el.title, el.count]);
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
  background: #fff;
}
</style>
