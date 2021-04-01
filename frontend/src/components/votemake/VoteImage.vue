<template>
    <div>
          
          <b-form-file
            v-model="fileId"
            placeholder="첨부파일 없음"
            drop-placeholder="Drop file here..."
            required
            accept=".jpg, .png, .gif"
            @change="previewImage"
            style="width: 70%;"
          ></b-form-file>
          <img :src="previewImageData" />
          <input type="button" @click="remove(this)" value="삭제하기">
          <input class="content-title-input1" type="text" placeholder="제목">
          <input class="content-title-input2" type="text" placeholder="내용">
    </div>
</template>

<script>
export default {
    data() {
        return {
            previewImageData: "",
        }
    },
    methods:{
        previewImage(event) {
      
            var input = event.target;
            if (input.files && input.files[0]) {
            var reader = new FileReader();
                reader.onload = (e) => {
                console.log(e)
                this.previewImageData = e.target.result;
                };
                reader.readAsDataURL(input.files[0]);
            } 
            else {
                this.previewImageData = null;
            }
        },
    }
}
</script>