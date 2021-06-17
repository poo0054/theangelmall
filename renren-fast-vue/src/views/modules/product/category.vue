<template>
  <div>
    <el-tree :data="data" node-key="catId" :props="defaultProps" :expand-on-click-node="false" show-checkbox
             :default-expanded-keys="treekeys">
        <span class="custom-tree-node" slot-scope="{ node, data }">
      <span>{{ node.label }}</span>
      <span>
          <el-button v-if="node.level < 3" type="text" size="mini" @click="append(data)"> Append </el-button>
          <el-button v-if="node.childNodes.length == 0 " type="text" size="mini"
                     @click="remove(node, data)"> Delete </el-button>
        </span>
    </span>
    </el-tree>
  </div>
</template>

<script>
export default {
  name: 'category',
  watch: {},
  data() {
    return {
      data: [],
      defaultProps: {
        children: 'children',
        label: 'name',
      },
      treekeys: []
    }
  },
  methods: {
    getMenus() {
      // 获取数据列表
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/product/category/tree'),
        method: 'get',
      }).then(({data}) => {
        this.data = data.data
      })
    },

    append(data) {
      console.log("append", data)
    },

    //删除菜单
    remove(node, data) {
      this.$confirm(`是否删除[${node.data.name}]?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var ids = [node.data.catId];
        this.$http({
          url: this.$http.adornUrl('/product/category/delete'),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
            })
            this.getMenus()
            this.treekeys = [node.parent.data.catId]

          } else {
            this.$message.error(data.msg)
          }
        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
      console.log("remove", node, data)
    },
  },
  created() {
    this.getMenus();
  },
}
</script>

<style scoped>

</style>
