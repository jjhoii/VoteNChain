import axios from "axios";
import router from "@/router/index.js";
import { Utils } from "@/utils/index.js";

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

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
      state.isLogined = true;
      state.user.userIdx = payload.userIdx;
      state.user.userEmail = payload.userEmail;
      state.user.userName = payload.userName;
      state.user.account = payload.account;
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
    async getSocialUserinfo(context, user) {
      const response = await axios({
        method: "post",
        url: `${SERVER_URL}/user/checkUser`,
        data: {
          userEmail: user.userEmail,
          userName: user.userName,
          account: user.account,
        },
      });
      if (response.data["message"] == "needSignup") {
        const resp = await Utils.createAccount();
        user.account = resp;
        context.commit("setUserinfo", user);
        await context.dispatch("socialSignup", user);
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

    async socialSignup(context, user) {
      try {
        await axios({
          method: "post",
          url: `${SERVER_URL}/user/signup`,
          data: {
            userEmail: user.userEmail,
            userName: user.userName,
            account: user.account,
          },
        });
        await context.dispatch("getSocialUserinfo", user);
      } catch (err) {
        console.log(err);
      }
    },

    logout(context) {
      localStorage.clear();
      context.commit("logout");
      axios.defaults.headers.common["access-token"] = undefined;
      router.push("/");
      window.location.reload();
    },
  },
};
export default userStore;
