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
          <!-- <img :src="previewImageData" /> -->
          <!-- <input type="button" @click="remove(this)" value="삭제하기"> -->

          <div class="form-floating" style="margin-top: 15px; margin-bottom:15px" >
            <textarea class="form-control" placeholder="부가 설명을 입력해주세요." v-model="list.content" @change="changed" id="floatingTextarea"></textarea>
          </div>
          <input type="button" @click="test()" value="삭제하기">
          
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

        }
    },
    props:["list"],
    methods:{
        changed(){

            console.log("Emit: ", this.list );
            // this.$parent.previewImage();
            this.$emit("changed",this.list);
        },
    }
}
</script>
