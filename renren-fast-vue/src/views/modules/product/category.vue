<template>
  <div>
    <el-switch
      v-model="draggable"
      active-color="#13ce66"
      active-text="开启拖拽"
      inactive-color="#ff4949"
      inactive-text="关闭拖拽"
      @change="updateswitch">
    </el-switch>
    <el-button v-if="draggable" size="mini" @click="saveUpdateSolt">保存拖拽</el-button>
    <el-button size="mini" type="danger" @click="batDel">批量删除</el-button>
    <el-tree ref="menuTree" :allow-drop="allowDrop" :data="data" :default-expanded-keys="treekeys"
             :draggable="draggable"
             :expand-on-click-node="false"
             :props="defaultProps"
             node-key="catId"
             show-checkbox @node-drop="handleDrop">
        <span slot-scope="{ node, data }" class="custom-tree-node">

      <span>{{ node.label }}</span>
      <span>
          <el-button v-if="node.level < 3" size="mini" type="text" @click="append(data)"> 新增 </el-button>
          <el-button size="mini" type="text" @click="edit(data)"> 修改 </el-button>
          <el-button v-if="node.childNodes.length == 0 " size="mini" type="text"
                     @click="remove(node, data)"> 删除
          </el-button>
        </span>
    </span>
    </el-tree>

    <el-dialog
      ref="dialogref"
      :before-close="handleClose"
      :title="elDialog.title"
      :visible.sync="elDialog.dialogVisible"
      width="60%">
      <el-form :model="category" inline>
        <el-row>
          <el-col :span="12">
            <el-form-item label="分类名称" prop="name">
              <el-input v-model="category.name" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计量单位" prop="productUnit">
              <el-input v-model="category.productUnit" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="图标地址" prop="icon">
              <el-input v-model="category.icon" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否可用" prop="showStatus">
              <el-switch
                v-model="category.showStatus"
                :active-value='0'
                :inactive-value='1'
                active-text="开"
                inactive-text="关">
              </el-switch>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="排序" prop="sort">
              <el-input-number v-model="category.sort" label="描述文字"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <span slot="footer">
    <el-button @click="elDialog.dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="addcategory">确 定</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>
import http from "../../../utils/httpRequest";

export default {
  name: 'category',
  watch: {},
  data() {
    return {
      //是否开启拖拽
      draggable: false,
      //所有修改的节点
      updateNode: [],
      //最大深度
      level: 1,
      category: {},
      elDialog: {
        dialogVisible: false,
        title: null,
      },
      data: [],
      defaultProps: {
        children: 'children',
        label: 'name',
      },
      //默认打开的节点
      treekeys: [],
      //拖拽后父id
      pCid: 0
    }
  },
  methods: {
    //批量删除
    batDel() {
      let checkedKeys = this.$refs.menuTree.getCheckedKeys()
      console.log("被选中的元素", checkedKeys)
      this.$confirm(`是否删除批量删除?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/product/category/delete'),
          method: 'post',
          data: this.$http.adornData(checkedKeys, false)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message.success(data.msg)
            this.getMenus()
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
    },
    //开启关闭拖拽
    updateswitch() {
      this.getMenus()
    },
    //批量保存拖拽
    saveUpdateSolt() {
      console.log("this.updateNode ", this.updateNode)
      this.$http({
        url: this.$http.adornUrl('/product/category/update/list'),
        method: 'post',
        data: this.$http.adornData(this.updateNode, false)
      }).then(({data}) => {
        if (data) {
          this.$message.success(data.msg)
          this.treekeys = [this.pCid]
          this.updateNode = []
          this.getMenus()
        } else {
          this.$message.error(data.msg)
        }
      })
    },
    //拖拽成功后
    handleDrop(draggingNode, dropNode, dropType, ev) {
      console.log('tree drop: ', draggingNode, dropNode, dropType);
      //当前节点父节点ID
      //拖拽后的兄弟节点
      let afterNode = null
      if (dropType == "inner") {
        this.pCid = dropNode.data.catId
        afterNode = dropNode.childNodes
      } else {
        this.pCid = dropNode.parent.data.catId == undefined ? 0 : dropNode.parent.data.catId
        afterNode = dropNode.parent.childNodes
      }

      for (let i = 0; i < afterNode.length; i++) {
        //改变后的当前节点
        if (draggingNode.data.catId == afterNode[i].data.catId) {
          //当前节点层级
          let catlevel = draggingNode.level
          //当前节点的层级发生变化
          if (draggingNode.level != afterNode[i].level) {
            catlevel = afterNode[i].level
            //当前节点层级子节点
            this.updateChildLevel(afterNode[i]);
          }
          this.updateNode.push({
            catId: afterNode[i].data.catId,
            sort: i,
            parentCid: this.pCid,
            catlevel: catlevel,
          })
        } else {
          this.updateNode.push({
            catId: afterNode[i].data.catId,
            sort: i
          })
        }
      }
    },

    /**
     * 拖拽后子节点改变
     * @param node 拖拽后的兄弟节点
     */
    updateChildLevel(node) {
      if (node.childNodes != null && node.childNodes.length > 0) {
        for (let i = 0; i < node.childNodes.length; i++) {
          console.log(`node.childNodes[${i}]`, node.childNodes[i])
          this.updateNode.push({
            catId: node.childNodes[i].data.catId,
            catLevel: node.childNodes[i].level,
            s: 's'
          })
          this.updateChildLevel(node.childNodes[i])
        }
      }
    },

    //判断节点是否可拖动
    allowDrop(draggingNode, dropNode, type) {
      this.level = 1;
      console.log("allowDrop", draggingNode, dropNode, type)
      this.getcountNodelevel(draggingNode.data)
      console.log("当前节点最大深度", this.level)
      this.level = this.level - draggingNode.level + 1
      console.log("当前节点要拖动的深度", this.level)
      if (type == "inner") {
        return (this.level + dropNode.level) <= 3
      } else {
        return (this.level + dropNode.parent.level) <= 3
      }
    },
    /**
     * 找到最大深度
     * @param node  当前拖动节点信息
     */
    getcountNodelevel(node) {
      if (node.children != null && node.children.length > 0) {
        for (let i = 0; i < node.children.length; i++) {
          if (node.children[i].catLevel > this.level) {
            this.level = node.children[i].catLevel
          }
          this.getcountNodelevel(node.children[i])
        }
      } else {
        if (node.catLevel > this.level) {
          this.level = node.catLevel
        }
      }
    },
    // 修改按钮
    edit(data) {
      this.$http({
        url: this.$http.adornUrl(`/product/category/info/${data.catId}`),
        method: 'get',
      }).then(({data}) => {
        if (data) {
          console.log(data)
          this.resetCategory();
          this.elDialog.title = `修改商品分类`
          this.category = {...data.data}
          console.log(this.category)
          this.elDialog.dialogVisible = true
        } else {
          this.$message.error(data.msg)
        }
      })
    },
    //新增按钮
    append(data) {
      this.resetCategory();
      this.elDialog.title = `新增商品分类`
      this.category.catLevel = data.catLevel * 1 + 1
      this.category.parentCid = data.catId
      this.elDialog.dialogVisible = true
    },

    //新增修改商品
    addcategory() {
      if (this.category.catId) {
        this.$http({
          url: this.$http.adornUrl('/product/category/update'),
          method: 'post',
          data: this.$http.adornData(this.category, false)
        }).then(({data}) => {
          if (data) {
            this.$message.success(data.msg)
            this.treekeys = [this.category.parentCid]
            this.elDialog.dialogVisible = false
            this.getMenus()
          } else {
            this.$message.error(data.msg)
          }
        })
      } else {
        this.$http({
          url: this.$http.adornUrl('/product/category/save'),
          method: 'post',
          data: this.$http.adornData(this.category, false)
        }).then(({data}) => {
          if (data) {
            this.$message.success(data.msg)
            this.treekeys = [this.category.parentCid]
            this.elDialog.dialogVisible = false
            this.getMenus()
          } else {
            this.$message.error(data.msg)
          }
        })
      }
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {
        });
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
            this.$message.success(data.msg)
            this.treekeys = [node.parent.data.catId]
            this.getMenus()
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
    },

    resetCategory() {
      this.category = {
        catId: null,
        name: null,
        catLevel: 0,
        parentCid: 0,
        showStatus: 0,
        icon: null,
        sort: 0,
        productUnit: null,
      }
    },

    //获取所有分类
    getMenus() {
      // 获取数据列表
      this.dataListLoading = true
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

  }
  ,
  created() {
    this.getMenus();
  }
  ,
}
</script>

<style scoped>

</style>
