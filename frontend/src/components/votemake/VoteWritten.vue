<template>
    <div style="margin-bottom: 15px">
        <div class="mb-3">
            <label for="exampleFormControlInput1" class="form-label" >항목명</label>
            <input type="email" class="form-control" id="exampleFormControlInput1" v-model="list.subject" @change="changed" placeholder="항목명을 입력해주세요.">
        </div>
            <!-- <b-form-file
                v-model="list.image"
                placeholder="첨부파일 없음"
                drop-placeholder="Drop file here..."
                required
                accept=".jpg, .png, .gif"
                @change="changed"
                
                
            ></b-form-file> -->
          <input id="upload-itemImage" ref="file" type="file" accept=".jpg, .png, .gif" @change="previewImage">
          <img :src="previewImageData" />
          <!-- <input type="button" @click="remove(this)" value="삭제하기"> -->

          <div class="form-floating" style="margin-top: 15px; margin-bottom:15px" >
            <textarea class="form-control" placeholder="부가 설명을 입력해주세요." v-model="list.content" @change="changed" id="floatingTextarea"></textarea>
          </div>
          <input type="button" @click="deleteSubject()" value="삭제하기">
          
    </div>
</template>


<script>
import AWS from 'aws-sdk';

export default {
    data() {
        return {
            // list:{
            //     subject:"",
            //     image:"",
            //     content:"",
            // }
            previewImageData: "",
            itemImage : null,

            bucketName: 'vncbucket',
            bucketRegion: 'ap-northeast-2',
            IdentityPoolId: 'ap-northeast-2:de2bc69f-a616-4734-a2c5-1d7bc1b95350',
        }
    },
    props:["list", "index"],
    methods:{
        changed(){

            console.log("Emit: ", this.list );
            // this.$parent.previewImage();
            this.$emit("changed",this.list);
        },
        deleteSubject(){
            this.$emit("deleteIndex",this.index);


        },
        uploadItemImage(){
            this.itemImage = this.$refs.file.files[0];
            console.log(this.itemImage, '파일 업로드');

            AWS.config.update({
                region: this.bucketRegion,
                credentials: new AWS.CognitoIdentityCredentials({
                IdentityPoolId: this.IdentityPoolId
                })
            });

            var s3 = new AWS.S3({
                apiVersion: "2006-03-01",
                params: { 
                Bucket: this.bucketName 
                }
            });

            let imageName = this.itemImage.name
            let imageKey = 'images/' + Date.now().toString() + '_' + imageName

            console.log(imageKey);

            s3.upload({
                Key : imageKey,
                Body : this.itemImage,
                ACL : 'public-read'
            }, (err, data) => {
                if (err){
                console.log(err);
                } else{
                this.list.image = data.Location;
                this.$emit("changed",this.list);
                //this.mainImagePath= data.Location;
                //console.log('mainImagePath : ' + this.mainImagePath);
                }      
            });
        },  
        previewImage(event) {
        var input = event.target;
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = (e) => {
            this.previewImageData = e.target.result;
            };
            reader.readAsDataURL(input.files[0]);

            console.log("uploadItemImage start");
            this.uploadItemImage();
            console.log("uploadItemImage end");
        } else {
            this.previewImageData = null;
            }
        },
    }
}
</script>
