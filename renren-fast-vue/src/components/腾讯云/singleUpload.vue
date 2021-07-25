<template>
  <div>
    <el-upload
      action="http://gulimall.oss-cn-shanghai.aliyuncs.com"
      :multiple="false"
      :file-list="fileList"
      list-type="picture"
      :on-change="handleChange"
      :auto-upload="false"
      :on-remove="handleRemove"
      :before-remove="beforeRemove"
      :before-upload="beforeUpload"
      :on-preview="handlePreview"
      :on-exceed="handleExceed"
      :on-success="handleImageSuccess"
    >
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过10MB</div>
    </el-upload>

    <el-dialog :append-to-body="true" :visible.sync="dialogVisible">
      <el-image width="100%" :src="dialogImageUrl"/>
    </el-dialog>
  </div>
</template>
<script>

import {getUUID} from '@/utils'
import {cospush} from "../../utils/cosUtils";

export default {
  name: 'singleUpload',
  props: {
    value: String
  },
  computed: {
    imageUrl() {
      return this.value;
    },
    imageName() {
      if (this.value != null && this.value !== '') {
        return this.value.substr(this.value.lastIndexOf("/") + 1);
      } else {
        return null;
      }
    },
    fileList() {
      return [{
        name: this.imageName,
        url: this.imageUrl
      }]
    },
  },
  data() {
    return {
      dialogImageUrl: null,
      dialogVisible: false,
    };
  },
  created() {

  },
  watch: {},
  methods: {
    handleImageSuccess(a, b) {
      console.log(a, b)
    },
    /**
     * 选择文件时，往fileList里添加
     */
    handleChange(fileList) {

      let imgName = (`${this.titleName}/` + getUUID()) + (fileList.type === 'image/jpeg' ? ".jpg" : ".png")
      let data = {
        uid: fileList.uid,
        name: this.$coshttp + imgName,
        url: fileList.url,
      }
      cospush(imgName, fileList.raw).then(result => {
        this.$message("图片上传成功")
        console.log(result)
        if (result.statusCode == 200) {
          //this.fileList.push(data);
          this.emitInput(fileList.url)
        }
      });
      this.fileList.pop();
      this.fileList.splice(0, this.fileList.length);
      this.emitInput(this.fileList.url)
    },

    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    emitInput(val) {
      this.$emit('input', val)
    }
    ,
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    }
    ,
    handleRemove(file, fileList) {
      this.emitInput('');
    }
    ,
    handlePreview(file) {
      this.dialogVisible = true;
      this.dialogImageUrl = file.url;
    }
    ,
    beforeUpload(file) {


      const isJPG = (file.type === 'image/jpeg' | file.type === 'image/png');
      const isLt2M = file.size / 1024 / 1024 < 5;
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 或者 PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isLt2M && isJPG;
    },
  }
}
</script>
<style>

</style>


