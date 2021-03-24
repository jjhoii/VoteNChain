<template>
  <div id="app">
    <div class="total">
      <HNav />
      <router-view class= 'content'/>
      <FootBar class="footbar" />
    </div>
  </div>
</template>
<script>  
import HNav from "@/components/common/HNav";
import FootBar from "@/components/common/FootBar"
export default {
    components: {
    FootBar,
    HNav,
  }
}
</script>
<style>
#app {

  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}

.form-group .content {
  font-size: 1.2rem;
}
.total{
  position: relative;
}
.content{
  height: 1500px;
}
.footbar{
   position: absolute;

    left: 0;

    bottom: 0;

    width: 100%;
  margin: 0px 5;
	padding: 15px 0;

	text-align: center;

	

	background: #333;
  
}
</style>
