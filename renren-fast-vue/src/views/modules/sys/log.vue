<template>
  <div class="mod-log">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="用户名／用户操作" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="dataListLoading" :data="dataList" border style="width: 100%">
      <el-table-column align="center" header-align="center" label="ID" prop="id" width="80">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="追踪ID" prop="traceId">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="业务类型" prop="businessType">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="方法名称" prop="methodName">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="操作类别" prop="operatorType">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="操作人员" prop="operName">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="请求URL" prop="operUrl">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="主机地址" prop="operIp">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="操作地点" prop="operLocation">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="请求参数" prop="operParam">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="返回参数" prop="jsonResult">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="操作状态" prop="status">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="错误消息" prop="errorMsg">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="操作时间" prop="operTime">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="消耗时间秒数" prop="costTime">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="创建人" prop="createBy">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="创建时间" prop="createDate">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="更新人" prop="updateBy">
      </el-table-column>
      <el-table-column align="center" header-align="center" label="更新时间" prop="updateDate">
      </el-table-column>
    </el-table>
    <el-pagination :current-page="pageIndex" :page-size="pageSize" :page-sizes="[10, 20, 50, 100]"
                   :total="totalPage" @size-change="sizeChangeHandle" @current-change="currentChangeHandle"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dataForm: {
        key: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      selectionDataList: []
    }
  },
  created() {
    this.getDataList()
  },
  methods: {

    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/sys/log/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'key': this.dataForm.key
        })
      }).then(({data}) => {
        if (data && data.code === '00000') {
          this.dataList = data.page.list
          this.totalPage = data.page.totalCount
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getDataList()
    }
  }
}
</script>
