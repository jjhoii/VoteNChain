import axios from "axios";
import router from "@/router/index.js";
import { Utils } from "@/utils/index.js";

const SERVER_URL = "http://localhost:8080";

const userStore = {
  namespaced: true,

  state: {
    isLogined: false,
    user: {
      userIdx: "",
      userEmail: "",
      userName: "",
      account: "",
    },
  },
  mutations: {
    setUserinfo(state, payload) {
      console.log("진입");
      console.log(payload);
      state.isLogined = true;
      state.user.userIdx = payload.userIdx;
      state.user.userEmail = payload.userEmail;
      state.user.userName = payload.userName;
      state.user.account = payload.account;
      //state.user.userIdx=payload["user"].userIdx;
      //state.user.userEmail=payload["user"].userEmail;
      //state.user.userName=payload["user"].userName;
      //state.user.logintype=payload["user"].loginType;
      //state.user.privateKey=payload["user"].privateKey;
      // localStorage.setItem("uid",payload["user"].uid);
    },
  },

  getters: {
    getIsLogined(state) {
      return state.isLogined;
    },
    getUserId(state) {
      return state.user.userIdx;
    },
    getEmail(state) {
      return state.user.userEmail;
    },
    getUsername(state) {
      return state.user.userName;
    },
    getPhone(state) {
      return state.user.account;
    },
  },

  actions: {
    /* inserttest(context, user){
            console.log("잘 들어 오니??")
            console.log(user.userEmail);
            console.log(user.userName);
            console.log(user.logintype);
            axios({
                method: "post",
                url: `${SERVER_URL}/user/test`,
                data: {
                    userEmail: user.userEmail,
                    userName: user.userName,
                    loginType: user.logintype,
                    privateKey: "123"
                },
            })
            .then((response) =>{
                if(response.data["message"]=="SUCCESS"){
                    alert("insert success");
                }
            }).catch((error) =>{
                alert("error");
                console.log(error);
            })
        },*/
    //로그인성공시 토큰 정보 받기
    /*login(context,user){
            axios({
                method: "post",
                url: `${SERVER_URL}/user/login`,
                data: {
                   email: user.email,
                   password: user.password,
                },
           })
          .then((response) => {
            localStorage.setItem("access_token", response.data["access-token"])
            localStorage.setItem("isLogin", true)
            axios.defaults.headers.common["access-token"]=`${response.data["access-token"]}`;
            context.dispatch("getUserinfo");
            router.push('/mainfeed');
          }).catch((error) => {
            alert("이메일과 비밀번호를 확인하세요");
            console.log(error);
          })
        },*/

    // 받은 토큰으로 유저 정보 가져오기
    /* getUserinfo(context){
            let token=localStorage.getItem("access_token")
            axios({
                method: "post",
                url: `${SERVER_URL}/user/checkJwt`,
                data : token,
            }).then((response)=>{
                context.commit("setUserinfo",response.data);
            }).catch(()=>{
            })
        },*/

    // async getSocialUserinfo(context, user) {
    //     try {
    //         const response = await axios.post({
    //             method:"post",
    //             url:`${SERVER_URL}/user/checkUser`,
    //             data: {
    //                 userEmail: user.userEmail,
    //                 userName: user.userName,
    //                 loginType: user.logintype,
    //                 privateKey: user.privateKey,
    //             }
    //         })
    //         console.log(response);
    //     } catch (error){
    //         console.error(error);
    //     }
    // },
    async getSocialUserinfo(context, user) {
      const self = this;
      // const temp = await Utils.createAccount();
      // console.log(temp);
      const response = await axios({
        method: "post",
        url: `${SERVER_URL}/user/checkUser`,
        data: {
          userEmail: user.userEmail,
          userName: user.userName,
          account: user.account,
        },
      });
      console.log(response.data["message"]);
      if (response.data["message"] == "needSignup") {
        console.log(user.account);
        const resp = await Utils.createAccount();
        user.account = resp;
        console.log("account : " + user.account);
        context.commit("setUserinfo", user);
        context.dispatch("socialSignup", user);
      }
      //자동 로그인
      else if (response.data["message"] == "success") {
        localStorage.setItem("access_token", response.data["access-token"]);
        localStorage.setItem(
          "myData",
          JSON.stringify({
            address: response.data["userAccount"],
          })
        );

        localStorage.setItem("isLogin", true);
        axios.defaults.headers.common[
          "access-token"
        ] = `${response.data["access-token"]}`;
      }
    },

    socialSignup(context, user) {
      axios({
        method: "post",
        url: `${SERVER_URL}/user/signup`,
        data: {
          userEmail: user.userEmail,
          userName: user.userName,
          account: user.account,
        },
      })
        .then((res) => {
          context.dispatch("getSocialUserinfo", user);
        })
        .catch((error) => {
          console.log(error);
          alert(error.data["message"]);
        });
    },

    logout(context) {
      localStorage.clear();
      context.commit("logout");
      axios.defaults.headers.common["access-token"] = undefined;
      router.push("/");
      window.location.reload();
    },
  },

  /*async SIGNUP(context, credentials){
            try {
                context.commit('START_LOADING')
                context.commit('START_SPINNER')
                const response = await requestJoinMember(credentials)  
                setTimeout(function () {
                    context.commit('SET_SNACKBAR', setSnackBarInfo('회원가입이 완료되었습니다.', 'light-green', 'buttom'))
                    context.commit('END_SPINNER')
                    context.commit('END_LOADING')
                    router.push('/')
                    return response                    
                }, 1000)
            } catch (e) {
                context.commit('END_LOADING')
                context.commit('OPEN_MODAL', {title: '회원가입 실패', content: e.response.data.message, option1: '닫기',})
            }
        },

        // 회원 상세 정보 요청 
        GET_MEMBER(context, targetUid) {
            context.commit('START_LOADING')
            context.commit('START_SPINNER')
            axios.get(`${SERVER_URL}/user/${targetUid}`)
            .then(res => {
                context.commit('END_SPINNER')
                context.commit('END_LOADING')
                // context.commit('SET_SNACKBAR', setSnackBarInfo('상세 정보 요청이 완료 되었습니다.', 'light-green', 'bottom'))
                const targetInfo = {uid: res.data.userInfo.uid, email: res.data.userInfo.email,
                name: res.data.userInfo.name, phone: res.data.userInfo.phone,
                profileUrl: res.data.userInfo.profileUrl}
                context.commit('PUT_TARGET_INFO', targetInfo)
            })
            .catch(err => {
                context.commit('END_SPINNER')
                context.commit('END_LOADING')
                context.commit('OPEN_MODAL', {title: '회원 정보 요청 실패', content: err, option1: '닫기',})
            })
            // 일치 확인
                if (targetUid === parseInt(localStorage.getItem("uid"))) { 
                context.commit('MYSELF')
              } else {
                context.commit('NOT_ME')
              }
        }, */
};
export default userStore;
