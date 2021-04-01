<template>
    <div style="margin-bottom: 15px">
        <div class="mb-3">
            <label for="exampleFormControlInput1" class="form-label" >항목명</label>
            <input type="email" class="form-control" id="exampleFormControlInput1" v-model="list.subject" @change="changed" placeholder="항목명을 입력해주세요.">
        </div>
            <b-form-file
                v-model="list.image"
                placeholder="첨부파일 없음"
                drop-placeholder="Drop file here..."
                required
                accept=".jpg, .png, .gif"
                @change="changed"
            ></b-form-file>
          <!-- <img :src="previewImageData2" /> -->
          <div class="form-floating" style="margin-top: 15px; margin-bottom:15px" >
            <textarea class="form-control" placeholder="부가 설명을 입력해주세요." v-model="list.content" @change="changed" id="floatingTextarea"></textarea>
          </div>
          <input type="button" @click="deleteSubject()" value="삭제하기">
          
    </div>
</template>


<script>
export default {
    data() {
        return {
            // list:{
            //     subject:"",
            //     image:"",
            //     content:"",
            // }
            // previewImageData2: "",
        }
    },
    props:["list", "index"],
    methods:{
        changed(){
            // this.previewImage2();
            console.log("Emit: ", this.list );
            // this.$parent.previewImage();
            this.$emit("changed",this.list);
        },
        deleteSubject(){
            this.$emit("deleteIndex",this.index);


        },
        // previewImage2(event) {
        //     var input = event.target;
        //     if (input.files && input.files[0]) {
        //         var reader = new FileReader();
        //         reader.onload = (e) => {
        //             this.previewImageData = e.target.result;
        //         };
        //         reader.readAsDataURL(input.files[0]);
        //         } else {
        //         this.previewImageData = null;
        //         }
        // },
    }
}
</script>
