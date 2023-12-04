<template>
  <div>
    <el-upload
      action="https://theangel-1306086135.cos.ap-guangzhou.myqcloud.com"
      :on-preview="handlePreview"
      :list-type="listType"
      :show-file="showFile"
      :limit="maxCount"
      :on-change="handleChange"
      :http-request="httpRequest"
      :on-remove="handleRemove"
      :before-remove="beforeRemove"
      :before-upload="beforeAvatarUpload"
      :on-exceed="handleExceed"
    >
      <i class="el-icon-plus"></i>
    </el-upload>

    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt/>
    </el-dialog>
  </div>
</template>
<script>

import {getUUID} from "@/utils/index"
import {cospush} from "@/utils/cosUtils.js"

export default {
  props: {
    showFile: {
      type: Boolean,
      default: true
    },
    listType: {
      type: String,
      default: 'picture-card',
    },
    titleName: {
      type: String,
      default: 'static'
    },
    //图片属性数组
    value: Array,
    //最大上传图片数量
    maxCount: {
      type: Number,
      default: 20
    }
  },
  watch: {},
  data() {
    return {
      fileList: [],
      dialogVisible: false,
      dialogImageUrl: null
    };
  },
  computed: {},
  mounted() {
  },
  methods: {
    httpRequest() {
      let value = [];
      for (let i = 0; i < this.fileList.length; i++) {
        value.push(this.fileList[i].imgName);
      }
      this.emitInput(value)
    },
    emitInput(value) {
      this.$emit("input", value);
    },
    /**
     * 选择文件时，往fileList里添加
     */
    handleChange(fileList) {
      let imgName = (`${this.titleName}/` + getUUID()) + (fileList.type === 'image/jpeg' ? ".jpg" : ".png")
      let data = {
        uid: fileList.uid,
        imgName: this.$coshttp + imgName,
        url: fileList.url,
      }
      cospush(imgName, fileList.raw)
      this.fileList.push(data);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    beforeAvatarUpload(file) {
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
    handleRemove(file, fileList) {
      this.fileList = this.fileList.filter(value => value.uid != file.uid);
      this.httpRequest()
    },
    handlePreview(file) {
      this.dialogVisible = true;
      this.dialogImageUrl = file.url;
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 ${this.maxCount}个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    }
  }
};
</script>

<style>

</style>


