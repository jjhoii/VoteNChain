<template>
  <div style=" position:relative; ">
    <div class="mb-3">
      <label for="exampleFormControlInput1" class="form-label votemake_font"
        >항목명</label
      >
      <input
        type="input"
        class="form-control"
        id="exampleFormControlInput1"
        v-model="list.title"
        @change="changed"
        placeholder="항목명을 입력해주세요."
      />
    </div>
    <div v-if="imageFlag" class="filebox">
      <label :for="index">업로드</label><br />
      <input
        type="file"
        ref="file"
        :id="index"
        accept=".jpg, .png, .gif"
        @change="previewImage"
      />
      <img v-if="previewImageData" class="preimg" :src="previewImageData" />
    </div>
    <div class="form-floating" style="margin-top: 15px; margin-bottom:15px">
      <textarea
        class="form-control"
        placeholder="부가 설명을 입력해주세요."
        v-model="list.description"
        @change="changed"
        id="floatingTextarea"
      ></textarea>
    </div>
    <input
      type="button"
      class="button-delete"
      @click="deleteSubject()"
      value="삭제"
    />
  </div>
</template>

<script>
import AWS from "aws-sdk";

export default {
  data() {
    return {
      previewImageData: "",
      itemImage: null,
      bucketName: "vncbucket",
      bucketRegion: "ap-northeast-2",
      IdentityPoolId: "ap-northeast-2:de2bc69f-a616-4734-a2c5-1d7bc1b95350",
    };
  },
  props: ["list", "index", "imageFlag"],

  methods: {
    changed() {

      this.$emit("changed", this.list);
    },
    deleteSubject() {
      this.$emit("deleteIndex", this.index);
    },
    uploadItemImage() {
      this.itemImage = this.$refs.file.files[0];

      AWS.config.update({
        region: this.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
          IdentityPoolId: this.IdentityPoolId,
        }),
      });

      var s3 = new AWS.S3({
        apiVersion: "2006-03-01",
        params: {
          Bucket: this.bucketName,
        },
      });

      let imageName = this.itemImage.name;
      let imageKey = "images/" + Date.now().toString() + "_" + imageName;


      s3.upload(
        {
          Key: imageKey,
          Body: this.itemImage,
          ACL: "public-read",
        },
        (err, data) => {
          if (err) {
            console.log(err);
          } else {
            this.list.imagePath = data.Location;
            this.$emit("changed", this.list);
          }
        }
      );
    },
    previewImage(event) {
      var input = event.target;

      if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = (e) => {
          this.previewImageData = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);

        this.uploadItemImage();
      } else {
        this.previewImageData = null;
      }
    },
  },
};
</script>

<style>
.button-delete {
  right: 0;
  top: 0;
  position: absolute;
  margin-top: 10px;
  border-width: 2px;
  border-color: red;
  background-color: white;
  border-radius: 20%;
  color: red;
  font-size: 15px;
}
.button-delete:hover {
  background: red;
  color: white;
}
.preimg {
  width: 300px;
  height: 300px;
  margin-bottom: 10px;
  margin-top: 10px;
}
</style>
