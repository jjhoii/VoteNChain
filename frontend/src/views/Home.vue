<template>
  <div>
    <div class="body">
      <header class="vid-header container">
        <div class="fullscreen-vid-wrap">
          <video
            src="video/VoteVideo.mp4"
            muted="muted"
            autoplay="true"
            loop="true"
          ></video>
        </div>
        <div class="header-overlay"></div>
        <div class="header-content">
          <img
            class="content-image"
            src="images/horizontal_on_white_by_logaster__3_-removebg-preview.png"
            alt=""
          />
          <h1>hello ! Vote And Chain</h1>
          <p>asdasdasdasdasdadasdasdadasda</p>
          <div style=" display:flex; justify-content: center;">
            <Button :word="word1" />
            <Button :word="word2" />
          </div>
          <div>
            <ListCard />
          </div>
        </div>
      </header>

      <div>
        <section class="section-a">
          <div class="container">
            <h1>ㅁㄴㅇㅁㄴㅇㅁㅇㅁ A</h1>
            <p>asdsadfskldfjasflgkjsdklfjasssdkl</p>
          </div>
        </section>
        <section></section>
        <div>
          <FootBar class="footbar" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ListCard from "../components/home/ListCard";
import FootBar from "../components/common/FootBar";
import Button from "@/components/home/Button";
export default {
  components: {
    Button,
    FootBar,
    ListCard,
  },
  data() {
    return {
      word1: "투표만들기",
      word2: "참여하기",
    };
  },
};
</script>

<style>
* {
  box-sizing: border-box;
}

.body {
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
  font-size: 1rem;
  line-height: 1.5;
  color: #333;
  overflow-x: hidden;
}
.vid-header {
  height: 100%;
  display: flex;
  align-items: center;
  color: #fff;
}

.container {
  max-width: 960px;
  padding-left: 1rem;
  padding-right: 1rem;
  margin: auto;
  text-align: center;
}

.fullscreen-vid-wrap {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.fullscreen-vid-wrap video {
  width: 100%;
  min-height: 100%;
}

.header-overlay {
  height: 100%;
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;

  background-color: black;
  z-index: 1;
  opacity: 0.35;
  text-align: center;
}
.header-content {
  z-index: 2;
  width: 100%;
}

.header-content h1 {
  font-size: 50px;
  margin-bottom: 0;
}
.header-content p {
  font-size: 1.5rem;
  display: block;
  padding-bottom: 2rem;
}
.content-image {
  width: 50%;
  height: 100%;
}

.section {
  padding: 20px 0;
}
.section-b {
  background: #333;
  color: #fff;
}

@media (max-width: 960px) {
  .container {
    padding-right: 3rem;
    padding-left: 3rem;
  }
}
</style>
