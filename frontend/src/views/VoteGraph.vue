<template>
  <div>
    <div class="graph-container">
      <div class="graph-content">
        <h1>{{ mainTitle }}</h1>
        <h1>메인 제목</h1> 
        <img :src="mainImagePath" alt="" style="width: 50%; height: 30%" />
        <p>
          {{ mainDescription }}
        </p>
        <p>
          메인내용 
        </p>

        <GChart type="PieChart" :data="chartData" :options="chartOptions" />
     

        <div>
          <b-button>참가자 목록</b-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { GChart } from "vue-google-charts";
import { Utils } from "@/utils/index.js";

export default {
  components: {
    GChart,
  },
  data() {
    return {
      mainTitle: "",
      mainDescription: "",
      mainImagePath: "",
      // Array will be automatically processed with visualization.arrayToDataTable function
      loaded: false,
      chartData: [
        ["Key", "Value"],
        ["2014", 1000],
        ["2015", 1170],
        ["2016", 660],
        ["2017", 1030],
      ],
      chartOptions: {
        chart: {
          title: "Company Performance",
          subtitle: "Sales, Expenses, and Profit: 2014-2017",
        },
      },
    };
  },
  created() {
    // 오류 발생 임시 주석 처리
    // google.charts.load("current", { packages: ["corechart"] });
    // google.charts.setOnLoadCallback(this.drawChart());
    this.getData(0);
  },
  methods: {
    async getData(idx) {
      this.web3 = Utils.web3;
      const contract = Utils.contract;

      // get vote data
      const rs = await contract.methods.getVote(idx).call();
      console.log(rs);

      // set data
      this.mainTitle = rs.title;
      this.mainDescription = rs.description;
      this.mainImagePath = rs.imagePath;

      // set chart
      this.chartData = [["Key", "Value"]];
      rs.items.forEach((el) => {
        this.chartData.push([el.title, el.count]);
      });

      // load complete
      this.loaded = true;
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
  },
};
</script>

<style>

.graph-container {
  padding-top : 100px;
  padding-right: 3rem;
  padding-left: 3rem;
  height: 100vh;
  display: flex;
  justify-content: center;
  background: gray;
}
.graph-content{
  
  text-align: center;
  width: 70%;
  background: #fff;
}
</style>
