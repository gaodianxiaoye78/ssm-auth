<template>
    <div class="app-container">
        角色列表
    </div>
</template>
<script>
//引入定义接口的js文件
import api from '@/api/system/role'

export default {
  //定义初始值
  data() {
    return {
      listLoading:false,//是否显示加载
      list:[],//角色列表
      total:0,//总记录数
      page:1,//当前页
      limit:3,//每页显示记录数
      searchObj:{},//条件查询封装对象

      dialogVisible: false,//弹出框
      sysRole:{}, //封装添加表单数据
      selectValue:[] //复选框选择内容封装数组
    }
  },
  //页面渲染之前执行
  created() {
    //调用列表方法
    this.fetchData()
  },
  methods:{//具体方法
    //跳转分配菜单权限
    showAssignAuth(row) {
      this.$router.push('/system/assignAuth?id='+row.id+'&roleName='+row.roleName);
    },
    //复选框发生变化执行方法
    handleSelectionChange(selection) {
      this.selectValue = selection
      //console.log(this.selectValue)
    },
    //批量删除
    batchRemove() {
      //判断
      if(this.selectValue.length==0) {
        this.$message.warning('请选择要删除的记录！')
        return
      }
      this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //数组
          var idList = []
          //获取多个复选框对应id，封装到数组里面
          // [1,2,3]
          for(var i=0;i<this.selectValue.length;i++) {
            var obj = this.selectValue[i]
            //id值
            var id = obj.id
            //放到数组里面
            idList.push(id)
          }
          api.batchRemove(idList).then(response => {
              //提示
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              //刷新
              this.fetchData()
          })
        })
    },
    //修改-数据回显
    edit(id) {
      //弹出框
      this.dialogVisible = true
      api.getRoleId(id).then(response => {
        this.sysRole = response.data
      })
    },
    //点击确定
    saveOrUpdate() {
      //判断添加还是修改
      if(!this.sysRole.id) {
        //添加
        this.saveRole()
      } else {
        this.updateRole()
      }
    },
    //修改的方法
    updateRole() {
      api.update(this.sysRole)
        .then(response => {
          //提示
          this.$message({
            type: 'success',
            message: '修改成功!'
          });
          //关闭弹框
          this.dialogVisible = false
          //刷新页面
          this.fetchData()
        })
    },
    //添加的方法
    saveRole() {
      api.saveRole(this.sysRole)
        .then(response => {
          //提示
          this.$message({
            type: 'success',
            message: '添加成功!'
          });
          //关闭弹框
          this.dialogVisible = false
          //刷新页面
          this.fetchData()
        })
    },
    //点击添加，弹出框
    add() {
      this.dialogVisible = true
      this.sysRole = {}
    },
    //删除
    removeDataById(id) {
       this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //调用方法删除
          api.removeId(id)
            .then(response => {
              //提示
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              //刷新
              this.fetchData()
          })
        })
    },
    //重置
    resetData() {
      //清空表单
      this.searchObj = {}
      //查询所有数据
      this.fetchData()
    },
    //条件分页查询列表
    //pageNum 查询页数
    fetchData(pageNum=1) {
      //页数赋值
      this.page = pageNum
      //ajax
      api.getPageList(this.page,this.limit,this.searchObj)
        .then(response => {
          //this.listLoading = false
          console.log(response)
          //每页数据列表
          this.list = response.data.records
          //总记录数
          this.total = response.data.total
        })
    }
  }
}
</script>