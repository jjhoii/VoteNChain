<template>
  <div id="app">
    <div >
      <HNav  />
      <div>
        <router-view />
      </div>
    </div>
  </div>
</template>
<script>
import HNav from "@/components/common/HNav";
export default {
  components: {
    HNav,
  },
};
</script>
<style>
.navbar {
  height: 10%;
  display: flex;
  flex-direction: row;
  
}
.navbar-icon {
  width: 15%;
  height: 100%;
}
.navbar-icon img {
  height: 90px;
}
.navbar-title{
  width: 35%;
  height: 100%;
}
.navbar-title a{
  color: #fff;
  font-size : 30px;
}
.navbar-list{
  width: 50%;
  height: 100%;
}
.navbar-list span{
  color:#fff;
  float: right;
  font-size : 16px;
  padding: 10px;
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
}

.footbar {
  left: 0;
  bottom: 0;
  width: 100%;
  padding: 15px 0;
  text-align: center;
  background: #333;
}
</style>
