<template>
  <div>
    <el-input
      placeholder="输入关键字进行过滤"
      v-model="filterText">
    </el-input>
    <el-tree ref="menuTree" :data="data"
             :props="defaultProps"
             @node-click="nodeClick"
             :filter-node-method="filterNode"
             node-key="catId">
    </el-tree>
  </div>
</template>

<script>
export default {
  watch: {
    filterText(val) {
      this.$refs.menuTree.filter(val);
    }
  },
  data() {
    return {
      filterText: null,
      data: [],
      defaultProps: {
        children: 'children',
        label: 'name',
      },
    }
  },
  methods: {
    /**
     * 节点搜索
     * @param value
     * @param data
     * @returns {boolean}
     */
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },

    /**
     * node点击事件
     * $emit 传递给父组件
     * @param data
     * @param node
     * @param component
     */
    nodeClick(data, node, component) {
      this.$emit("tree-nodeClick", data, node, component)
    },
    //获取所有分类
    getMenus() {
      // 获取数据列表
      this.$http({
        url: this.$http.adornUrl('/product/category/tree'),
        method: 'get',
      }).then(({data}) => {
        if (data) {
          this.data = data.data
        } else {
          this.$message.error(data.msg)
        }
      })
    },
  },
  created() {
    this.getMenus();
  }

}
</script>

<style scoped>

</style>
