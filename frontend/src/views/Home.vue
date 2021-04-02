<template>
  <div>
    

    <div class="body" >
    
      <section class="vid-header ">
        <div class="fullscreen-vid-wrap">
          <video
            src="video/MainVideo.mp4"
            muted="muted"
            autoplay="true"
            loop="true"
          ></video>
        </div>
   

      
        <!-- <div class="header-overlay"></div> -->
        <div class="header-content ">
          <strong>Block Chain <br />Vote Solution</strong>
          <p>Vote & Chain은 블록체인을 적용한 신뢰성있는 전자투표입니다.</p>
          <button @click="PageChange()">투표하기</button>
        </div>
      </section>
     

     
      <section class="section1">
        <div class="section-content">
          <p>Vote&Chain은 <br>
          <strong>블록체인을<br> 적용한<br>
          신뢰성있는 <br>
          전자투표</strong> 입니다.</p>
            
        </div>
        <div class="section-video">
           <video 
            src="video/video.mp4"
            muted="muted"
            autoplay="true"
            loop="true"
            style="width: 900px; "

          ></video>
        </div>
      </section>
    
    </div>
   
  </div>
</template>

<script>
export default {
  components: {
  },
  data() {
        return {
          
            items: ['x', 'y', 'z'],
            active: 0, // Define the active index
            deltaY: 0 // This is used for the scroll wheel navigation
        }
    },
    created() {
        // Register the `wheel` event
        window.addEventListener('wheel', this._handleWheel, { passive: true });
    },
    destroyed() {
        // Remove the `wheel` event
        window.removeEventListener('wheel', this._handleWheel, { passive: true });
    },
    watch: {
        // I have added watchers to both `deltaY` and `active`, however,
        // this may not be necessary. These will not create an endless loop
        // because the watcher is only called when a value is changed
        active: function(index) {
            // Whenever the `active` index changes, update the `deltaY` value
            this.deltaY = index * 35;
            console.log("asdasd")
        },
        deltaY: function(value) {
            // Whenever the `deltaY` value changes, update the `active` index
            this.active = Math.floor(value / 35);
               console.log("asdasd")
        }
    },
  methods:  {
    
    PageChange(){
        if(localStorage.getItem('auth-token') == undefined){
          this.$router.push("VoteMake");
        }
        else{
          this.$router.push("VoteMake"); 
        }
    },
    onScroll(){
      console.log("asdasd")
    },
    _handleWheel (event) {
     
            // The navigation is only active when the page has not
            // been scrolled
            if (document.documentElement.scrollTop === 0) {
                // If the last item is currently active then we do not need to
                // listen to `down` scrolls, or, if the first item is active, 
                // then we do not need to listen to `up` scrolls
                if (
                    (event.deltaY > 0 && (this.active - 1) === this.items.length)
                    || (event.deltaY < 0 && this.deltaY === 0)
                ) {
                    return;
                }
                this.deltaY += Math.sign(event.deltaY);
            }
        }
    // asdd(){
    // // box클래스 추출
    //         var elm = document.getElementsByClassName("box");
    //         // box클래스 개수만큼 실행
    //         for (var i = 0; i < elm.length; i++) {
    //             // box 에 각각 마우스 휠 감지
    //             // 휠감지
    //             elm[i].addEventListener("mousewheel", MouseWheelHandler, false);
    //             // firefox 용 휠처리
    //             elm[i].addEventListener("DOMMouseScroll", MouseWheelHandler, true);

    //         }
    // },
    // MouseWheelHandler(e) {
    //   // 스크롤 취소시킴(이걸 안할경우 도중에 명령을 받아 화면이 덜덜 거릴수 있음)
    //         e.preventDefault();
    //         // 휠값처리
    //         var delta = 0;
    //         if (!Event) Event = window.event;
    //         if (Event.wheelDelta) {
    //             delta = Event.wheelDelta / 120;
    //             if (window.opera) delta = -delta;
    //         }
    //         else if (Event.detail)
    //             delta = -Event.detail / 3;
 
    //         // 여러개일경우 다른 selector 을 확인하기위한 상위 dom 으로 이동
    //         var p = e.target.parentElement;
    //         // 몇번째 dom 인지 저장
    //         var index = Array.prototype.indexOf.call(p.children, e.target);
    //         // 같은 위치의 돔목록 을 저장
    //         var elmArr = e.target.parentElement.children;
    //         // 지금의 스크롤 위치 저장
    //         this.currentScroll = document.documentElement.scrollTop || document.body.scrollTop;
    //         // 다음위치의 좌표(기본이므로 현재의 Y 좌표 저장)
    //         var NextTarget = this.currentScroll;
    //         // 마우스휠 위로
    //         if (delta > 0) {
    //             // 맨처음 지점 제외
    //             if (index > 0) {
    //                 // 이전 dom 의 index 번호
    //                 var no = (index - 1);
    //                 // 좌표위치 저장
    //                 NextTarget = elmArr[no].offsetTop;
    //             }
    //         }
    //         // 마우스휠 아래로
    //         else if (delta < 0)
    //         {
    //             // 맨마지막 지점 제외
    //             if (index < elmArr.length - 1) {
    //                 // 다음 dom 의 index 번호
    //                 var no = (index + 1);
    //                 // 좌표위치 저장
    //                 NextTarget = elmArr[no].offsetTop;
    //             }
    //         }
    //         // 애니메이션
    //         // 필요없으면 바로 window.scrollTo(0, NextTarget);
    //         // 에니메이션 초기화
    //         clearInterval(this.tim);
    //         // 애니메이션 실행
    //         tim = setInterval(tran, 1);
    //         // 애니메이션 function
    //         function tran() {
    //             // 이동속도 숫자가 작아질수록 느려짐
    //             var speed = 5;
    //             // 현재 스크롤과 이동후 스크롤이 같으면 정지시킨다 
    //             if (this.currentScroll == NextTarget) {
    //                 clearInterval(tran);
    //             } else {
    //                 // 스크롤을 위로 올릴 경우
    //                 if (this.currentScroll - speed > NextTarget)
    //                 {
    //                     this.currentScroll -= speed;
    //                 }
    //                 // 스크롤을 내일 경우
    //                 else if (this.currentScroll + speed < NextTarget)
    //                 {
    //                     this.currentScroll += speed;
    //                 }
    //                 // 스크롤이 속도로 지정된 변수보다 작을 경우 강제적으로 맞춰준다
    //                 else
    //                 {
    //                     this.currentScroll = NextTarget;
    //                 }
    //                 // 스크롤위치 변경
    //                 window.scrollTo(0, this.currentScroll);
    //             }
    //         }
 
    // }
  }
}
</script>

<style>
* {
  box-sizing: border-box;
}

.body {
  display: flex;
  flex-direction: column;
  font-family: Roboto,'Noto Sans KR','Apple SD Gothic Neo',sans-serif;
  font-size: 1rem;
  height: 100vh;
  width: 100%;
  line-height: 1.5;
  color: #333;
  /* overflow-x: hidden; */
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
}

.fullscreen-vid-wrap video {
  
  width: 100%; 
  min-height: 100vh;
}

/* .header-overlay {
  height: 100vh;
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;

  background-color: black;
  z-index: -1;
  opacity: 0.35;
  text-align: center;
} */
.header-content {
  z-index: 2;
  padding-bottom: 23%;
  margin-left: 10%;
  margin-top: 10%;
  padding-top : 20%;
}
.header-content strong {
  font-size: 65px;
  line-height: 1.14em;
}
.header-content p {
  margin-top: 25px;
}
.header-content button {
  position:fixed;
  background-color: #ad1315;
  width: 150px;
  height: 150px;
  color: #fff;
  border-radius: 75px;
  text-align: center;
  margin: 0 auto;
  font-size: 20px;
  vertical-align: middle;
  line-height: 150px;
}
.section1{
  
  margin-top : 18vh;
  display: flex;
  width:  100%;
  background : #0151;
  height : 100vh;
 
}
.section-content{
  display: flex;
  align-items: center;
  text-align: left;
  height : 100vh;
  width: 40%;
  font-size : 48px;
  line-height: 1.55em;
  padding-left: 100px;
  padding-bottom: 50px;
 
}
.section-video{
  width: 60%;
  height: 85vh;
  background : #fff;
  display: flex;
 
  justify-items: center;
  align-items: center;
  margin: auto;
  
}
.section-video video{
  height: 100vh;
  width: 100% !important;
}
@media (max-width: 960px) {
  .container {
    padding-right: 3rem;
    padding-left: 3rem;
  }
}
</style>
