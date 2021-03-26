<template>
  <div>
    <h1>
      프로듀스 101 투표
    </h1>
    <div>
      <img
        src="https://source.unsplash.com/random"
        alt=""
        style="width:50%; height:30%"
      />
    </div>
    <p>
      마침내 세계가 놀랄 글로벌 아이돌을 탄생시킬 x의 베일이 벗겨진다. 글로벌
      아이돌 육성 프로젝트! 프로듀스X 101
    </p>

    <div>
      <h1>My Web Page</h1>

       <GChart
    type="PieChart"
    :data="chartData"
    :options="chartOptions"
  />
    </div>

    <div>
      <b-button>참가자 목록</b-button>
    </div>

    <div>
      <FootBar class="footbar" />
    </div>
  </div>
</template>



<script>
import FootBar from "@/components/common/FootBar";
import { GChart } from 'vue-google-charts'
 
export default {
  components: {
    FootBar,
    GChart
  },
  data() {
    return {
          // Array will be automatically processed with visualization.arrayToDataTable function
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
    };
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
  },
};
</script>

<style></style>
