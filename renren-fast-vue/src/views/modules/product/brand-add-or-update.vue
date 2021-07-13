<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="!dataForm.brandId ? '新增' : '修改'"
    :visible.sync="visible">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="80px"
             @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="品牌名" prop="name">
        <el-input v-model="dataForm.name" placeholder="品牌名"></el-input>
      </el-form-item>

      <el-form-item label="logo" prop="logo">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :before-remove="beforeRemove"
          :before-upload="beforeAvatarUpload"
          :headers="header"
          :limit="1"
          :on-change="handleChange"
          :on-exceed="handleExceed"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          action="http://localhost:88/api/product/category/files"
          multiple>
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>

      </el-form-item>

      <el-form-item label="介绍" prop="descript">
        <el-input v-model="dataForm.descript" placeholder="介绍"></el-input>
      </el-form-item>
      <el-form-item label="显示状态" prop="showStatus">
        <el-switch
          v-model="dataForm.showStatus"
          :active-value="0"
          :inactive-value="1"
          active-color="#13ce66"
          inactive-color="#ff4949"
        >
        </el-switch>
      </el-form-item>
      <el-form-item label="检索首字母" prop="firstLetter">
        <el-input v-model="dataForm.firstLetter" placeholder="检索首字母"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model.number="dataForm.sort" placeholder="排序"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>

import {generateUUID, cospush} from "../../../utils/cosUtils"

export default {
  data() {
    return {
      fileList: [],
      header: {},
      visible: false,
      dataForm: {
        brandId: 0,
        name: '',
        logo: '',
        descript: '',
        showStatus: '',
        firstLetter: '',
        sort: ''
      },
      dataRule: {
        name: [
          {required: true, message: '品牌名不能为空', trigger: 'blur'}
        ],
        logo: [
          {required: true, message: '品牌logo地址不能为空', trigger: 'blur'}
        ],
        descript: [
          {required: true, message: '介绍不能为空', trigger: 'blur'}
        ],
        showStatus: [
          {required: true, message: '显示状态[0-不显示；1-显示]不能为空', trigger: 'blur'}
        ],
        firstLetter: [
          {
            validator: (rule, value, callback) => {
              if ('' == value) {
                callback(new Error('首字母不能为空！'));
              } else if (!/^[a-zA-Z]$/.test(value)) {
                callback(new Error('首字母必须为字母'));
              } else {
                callback();
              }
            }, trigger: 'blur'
          }
        ],
        sort: [
          {
            validator: (rule, value, callback) => {
              if ('' == value) {
                callback(new Error('排序字段不能为空！'));
              } else if (!Number.isInteger(value) && value >= 0) {
                callback(new Error('排序字段必须为整数'));
              } else {
                callback();
              }
            }, trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    /**
     * 选择文件时，往fileList里添加
     */
    handleChange(fileList) {
      console.log("handleChange", fileList)
      this.fileList.push(fileList);
    },
    /**
     * 删除
     * @param file
     * @param fileList
     */
    handleRemove(file, fileList) {
      const data = []
      this.fileList.forEach(f => {
        if (f.uid !== file.uid) {
          data.push(f)
        }
      })
      this.fileList = data
    },
    /**
     * 点击文件列表中已上传的文件时的钩子
     * @param file
     */
    handlePreview(file) {
      console.log(file);
    },
    /**
     * 文件超出个数限制时的钩子
     * @param files
     * @param fileList
     */
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 10 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    /**
     * 删除文件之前的钩子，参数为上传的文件和文件列表，
     * @param file
     * @param fileList
     * @returns {Promise<MessageBoxData>}
     */
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },

    beforeAvatarUpload(file) {
      console.log(file.type)
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

    init(id) {
      this.dataForm.brandId = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.brandId) {
          this.$http({
            url: this.$http.adornUrl(`/product/brand/info/${this.dataForm.brandId}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.name = data.brand.name
              this.dataForm.logo = data.brand.logo
              this.dataForm.descript = data.brand.descript
              this.dataForm.showStatus = data.brand.showStatus
              this.dataForm.firstLetter = data.brand.firstLetter
              this.dataForm.sort = data.brand.sort
            }
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      console.log(this.fileList)
      let imgName = null;
      if (this.fileList && this.fileList.length > 0) {
        this.fileList.forEach((file) => {
          imgName = ("goods/" + generateUUID()) + (file.type === 'image/jpeg' ? ".jpg" : ".png")
          //赋值给图片
          this.dataForm.logo = "https://" + "theangel-1306086135.cos.ap-guangzhou.myqcloud.com/" + imgName
        })
      }

      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/product/brand/${!this.dataForm.brandId ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'brandId': this.dataForm.brandId || undefined,
              'name': this.dataForm.name,
              'logo': this.dataForm.logo,
              'descript': this.dataForm.descript,
              'showStatus': this.dataForm.showStatus,
              'firstLetter': this.dataForm.firstLetter,
              'sort': this.dataForm.sort
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              if (this.fileList && this.fileList.length > 0) {
                // 下面的代码将创建一个空的FormData对象:
                // 你可以使用FormData.append来添加键/值对到表单里面；
                this.fileList.forEach((file) => {
                  cospush(imgName, file.raw);
                })
              }
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.visible = false
                  this.$emit('refreshDataList')
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }
      })
    }
  }
}
</script>
