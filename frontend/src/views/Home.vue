<template>
  <div>
    <HNav />

    <div class="body">
      <section class="vid-header">
        <div class="fullscreen-vid-wrap">
          <video
            src="video/MainVideo.mp4"
            muted="muted"
            autoplay="true"
            loop="true"
          ></video>
        </div>

        <div class="header-content">
          <strong>Block Chain <br />Vote Solution</strong>
          <p>Vote & Chain은 블록체인을 적용한 신뢰성있는 전자투표입니다.</p>
          <button @click="PageChange()" class="button_status" id="button_make">
            투표 만들기
          </button>
        </div>
      </section>

      <section class="section1">
        <div class="section-content">
          <p>
            Vote&Chain은 <br />
            <strong
              >블록체인을<br />
              적용한<br />
              신뢰성있는 <br />
              전자투표</strong
            >
            입니다.
          </p>
        </div>
        <div class="section-video">
          <video
            src="video/bcvideo.mp4"
            muted="muted"
            autoplay="true"
            loop="true"
            style="width: 900px"
          ></video>
        </div>
      </section>
      <section class="section2">
        <div class="section-video">
          <video
            src="video/s2video.mp4"
            muted="muted"
            autoplay="true"
            loop="true"
            style="width: 900px"
          ></video>
        </div>
        <div class="section-content">
          <p>
            <strong>Vote&Chain</strong>을 <br />
            활용해<br />
            다양한 투표를<br />
            진행해보세요.<br />
          </p>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import HNav from "@/components/common/HNav";
export default {
  components: {
    HNav,
  },

  data() {
    return {
      items: ["x", "y", "z"],
      active: 0, // Define the active index
      deltaY: 0, // This is used for the scroll wheel navigation
    };
  },
  created() {
    // Register the `wheel` event
    window.addEventListener("wheel", this._handleWheel, { passive: true });
  },
  destroyed() {
    // Remove the `wheel` event
    window.removeEventListener("wheel", this._handleWheel, { passive: true });
  },
  watch: {
    // I have added watchers to both `deltaY` and `active`, however,
    // this may not be necessary. These will not create an endless loop
    // because the watcher is only called when a value is changed
    active: function(index) {
      // Whenever the `active` index changes, update the `deltaY` value
      this.deltaY = index * 35;
    },
    deltaY: function(value) {
      // Whenever the `deltaY` value changes, update the `active` index
      this.active = Math.floor(value / 35);
    },
  },
  methods: {
    PageChange() {
      console.log(localStorage.getItem("access_token"));
      console.log(localStorage.getItem("myData"));
      if (
        localStorage.getItem("access_token") == undefined ||
        localStorage.getItem("myData") == undefined
      ) {
        alert("로그인 후에 진행해주세요.");
        this.$bvModal.show("bv-modal-example");
      } else {
        this.$router.push("VoteMake");
      }
    },
    onScroll() {
    },
    _handleWheel(event) {
      // The navigation is only active when the page has not
      // been scrolled
      if (document.documentElement.scrollTop === 0) {
        // If the last item is currently active then we do not need to
        // listen to `down` scrolls, or, if the first item is active,
        // then we do not need to listen to `up` scrolls
        if (
          (event.deltaY > 0 && this.active - 1 === this.items.length) ||
          (event.deltaY < 0 && this.deltaY === 0)
        ) {
          return;
        }
        this.deltaY += Math.sign(event.deltaY);
      }
    },
  },
};
</script>

<style>
* {
  box-sizing: border-box;
}

.body {
  display: flex;
  flex-direction: column;
  font-family: Roboto, "Noto Sans KR", "Apple SD Gothic Neo", sans-serif;
  font-size: 1rem;
  height: 100vh;
  width: 100%;
  line-height: 1.5;
  color: #333;
}
.vid-header {
  height: 100vh;
  display: flex;
  align-items: center;
  color: #fff;
}

.fullscreen-vid-wrap {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -2;
  background-color: rgb(197, 172, 140);
}

.fullscreen-vid-wrap video {
  width: 100%;
  min-height: 100vh;
}

.header-content {
  z-index: 2;
  padding-bottom: 23%;
  margin-left: 10%;
  margin-top: 10%;
  padding-top: 20%;
}
.header-content strong {
  font-size: 65px;
  line-height: 1.14em;
}
.header-content p {
  margin-top: 25px;
  font-size: 23px;
}
.section1 {
  padding-top: 18vh;
  display: flex;
  width: 100%;
  background: #0151;
  height: 140vh;
}
.section2 {
  padding-top: 5vh;
  display: flex;
  width: 100%;
  background: #0151;
  height: 140vh;
  background-color: #eed8c8;
}
.section-content {
  display: flex;
  align-items: center;
  text-align: left;
  height: 100vh;
  width: 40%;
  font-size: 48px;
  line-height: 1.55em;
  padding-left: 100px;
  padding-bottom: 50px;
}
.section-video {
  width: 60%;
  height: 85vh;
  display: flex;

  justify-items: center;
  align-items: center;
  margin: auto;
}
.section-video video {
  height: 100vh;
  width: 100% !important;
}
@media (max-width: 960px) {
  .container {
    padding-right: 3rem;
    padding-left: 3rem;
  }
}
#button_make {
  width: 250px;
  height: 100px;
  margin-top: 30px;
  font-size: 30px;
  background-color: #bf7256;
  color: #ffffff;
}
#button_make:hover {
  box-shadow: none;
}
</style>
