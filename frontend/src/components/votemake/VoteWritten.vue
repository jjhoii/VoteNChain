<template>
    <div style="margin-bottom: 15px">
        <div class="mb-3">
            <label for="exampleFormControlInput1" class="form-label">항목명</label>
            <input type="email" class="form-control" id="exampleFormControlInput1" placeholder="항목명을 입력해주세요.">
        </div>
            <b-form-file
                v-model="fileId"
                placeholder="첨부파일 없음"
                drop-placeholder="Drop file here..."
                required
                accept=".jpg, .png, .gif"
                @change="previewImage"
            ></b-form-file>
          <img :src="previewImageData" />
          <!-- <input type="button" @click="remove(this)" value="삭제하기"> -->

          <div class="form-floating" style="margin-top: 15px; margin-bottom:15px" >
            <textarea class="form-control" placeholder="부가 설명을 입력해주세요." id="floatingTextarea"></textarea>
          </div>
          <input type="button" @click="Remove()" value="삭제하기">
    </div>
</template>


<script>
export default {
    data() {
        return {
            
        }
    },
    methods:{

    }
}
</script>
