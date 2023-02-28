<template>
  <!-- <h1>Award</h1> -->
  <div
    style="
      margin-left: 20%; 
      margin-top: 20px;
      <!-- background-color: aqua; -->
      <!-- border: 1px; -->
      text-align: center;
      width: 60%;
      display: flex;
      justify-content: center;
      flex-wrap: wrap;
    "
  >
    <!-- 第一个div 搜索和添加用户 -->
    <!-- 弹性布局 两个子元素两边排列 -->
    <div
      style="
        display: flex;
        justify-content: space-between;
        margin: 10px 0px;
        width: 100%;
      "
    >
      <!-- 搜索 -->
      <div>
        <!-- 输入框 可清除的 绑定的数据为v-model="employeeName" 回车搜索@keydown.enter.native="getEmps -->
        <!-- :disabled="showAdvanceSearchView" 如果是高级搜索的话此搜索不可用 -->
        <el-input
          placeholder="请输入员工名进行搜索，可以直接回车搜索..."
          prefix-icon="el-icon-search"
          clearable
          style="width: 350px; margin-right: 10px"
          v-model="searchName"
          @keydown.enter.native="getEcs"
        >
        </el-input>
        <el-button icon="el-icon-search" type="primary" @click="getEcs">
          搜索
        </el-button>
      </div>
      <!-- 添加奖惩信息 -->
      <div>
        <el-button type="primary" icon="el-icon-plus" @click="addEmpEc">
          添加奖惩
        </el-button>
      </div>
    </div>
    <!-- 第二个div 展示数据 -->
    <!-- 绑定数据 表头数据居中 内容数据居中 指定每行的类名，展示不同效果   -->
    <div style="width: 100%; margin-bottom: 10px">
      <el-table
        :data="empEcs"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
        :row-class-name="tableRowClassName"
      >
        <el-table-column property="id" label="编号" width="100" height="50px">
        </el-table-column>
        <el-table-column
          property="empName"
          label="姓名"
          width="100"
          height="50px"
        >
        </el-table-column>
        <el-table-column
          property="ecDate"
          sortable
          label="奖罚日期"
          width="200"
          height="50px"
        >
        </el-table-column>
        <el-table-column
          property="ecReason"
          label="奖罚原因"
          show-overflow-tooltip
          width="100"
          height="50px"
        >
        </el-table-column>
        <el-table-column
          property="ecPoint"
          label="奖罚分数"
          width="100"
          height="50px"
        >
        </el-table-column>
        <!-- 根据类别进行过滤 -->
        <el-table-column
          prop="ecType"
          label="奖罚类别"
          width="100"
          :filters="[
            { text: '惩罚', value: 1 },
            { text: '奖励', value: 0 },
          ]"
          :filter-method="filterTag"
          filter-placement="bottom-end"
        >
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.ecType === 1 ? 'primary' : 'success'"
              disable-transitions
              >{{ scope.row.ecType == 1 ? "惩罚" : "奖励" }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
          property="remark"
          label="备注"
          show-overflow-tooltip
          width="100"
          height="50px"
        >
        </el-table-column>
        <el-table-column label="操作" width="190" height="50px">
          <template slot-scope="scope">
            <el-button
              type="primary"
              @click="showEditEmpEcView(scope.row)"
              size="medium"
              style="padding: 4px"
              >编辑
            </el-button>
            <el-button
              type="danger"
              @click="deleteEmpEc(scope.row)"
              size="medium"
              style="padding: 4px"
              >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!--第三个div 分页工具展示-->
    <div>
      <!--            <span class="demonstration">完整功能</span>-->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page.pageNum"
        :page-sizes="[5, 10, 15, 20, 25, 30, 35, 40, 45, 50]"
        :page-size="page.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="page.total"
      >
      </el-pagination>
    </div>

    <!-- 添加和修改弹框 -->
    <el-dialog :title="title" :visible.sync="dialogVisible" @close="clearEmpEc">
      <div>
        <!-- 表单 数据校验 -->
        <el-form :model="empEc" :rules="rules" ref="empEcForm">
          <el-row>
            <el-col :span="6">
              <el-form-item label="姓名:">
                <!-- 绑定的数据为用户id 展示的数据label为用户名字 选择的值 value也为用户id -->
                <!-- 这样回显数据时 传回用户id可以显示该id的名字 -->
                <el-select
                  size="mini"
                  style="width: 150px"
                  v-model="empEc.eid"
                  filterable
                  placeholder="可按名字搜索"
                >
                  <el-option
                    v-for="item in options"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="奖罚类别:" prop="ecType">
                <!-- 绑定的数据empEc.ecType为整形 需要用:label这种形式 -->
                <el-radio-group v-model="empEc.ecType" style="margin-top: 13px">
                  <el-radio :label="0">奖励</el-radio>
                  <el-radio :label="1">惩罚</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="奖罚日期:" prop="ecDate">
                <!-- 指定日期格式：value-format="yyyy-MM-dd" -->
                <el-date-picker
                  v-model="empEc.ecDate"
                  size="mini"
                  type="date"
                  value-format="yyyy-MM-dd"
                  style="width: 150px"
                  placeholder="奖罚日期"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="奖罚原因:" prop="ecReason">
                <el-input
                  size="mini"
                  style="width: 150px"
                  prefix-icon="el-icon-edit"
                  v-model="empEc.ecReason"
                  placeholder="请输入奖罚原因"
                  clearable
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="奖罚分数:" prop="ecPoint">
                <el-input
                  size="mini"
                  style="width: 150px"
                  prefix-icon="el-icon-edit"
                  v-model="empEc.ecPoint"
                  placeholder="奖罚分数(0-100)"
                  clearable
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="备注:" prop="remark">
                <el-input
                  size="mini"
                  style="width: 150px"
                  prefix-icon="el-icon-edit"
                  v-model="empEc.remark"
                  placeholder="备注信息"
                  clearable
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="clearEmpEc">取 消</el-button>
        <el-button type="primary" @click="doAddEmpEc">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //名字选择 保存所有名字和员工id
      options: [],
      //修改和新增的标题
      title: "",
      //修改和新增弹框可见
      dialogVisible: false,
      // 查询参数
      searchName: "",
      //新增和修改奖惩对象
      empEc: {},
      //数据展示数组 存的是奖惩对象
      empEcs: [
        {
          id: 1,
          empName: "张三",
          ecDate: "2021-6-4 11:22:33",
          ecReason: "迟到迟到迟到",
          ecPoint: "张三",
          ecType: "奖励",
          remark: "张三",
        },
      ],
      //分页参数
      page: {
        pageNum: 1,
        pageSize: 10,
        total: 0,
      },
    };
  },
  methods: {
    ////分页展示相关方法
    //处理每页显示条数
    handleSizeChange(val) {
      this.page.pageSize = val;
      this.getEcs();
      console.log(`每页 ${val} 条`);
    },
    //处理页面切换
    handleCurrentChange(val) {
      this.page.pageNum = val;
      this.getEcs();
      console.log(`当前页: ${val}`);
    },
    //查找惩罚和奖励
    filterTag(value, row) {
      return row.ecType === value;
    },
    //绑定的每行的类名,根据每行的ecType进行判断
    tableRowClassName({ row }) {
      // 如果是奖励
      if (row.ecType === 0) {
        return "success-row";
      } else return "";
    },
    //获取所有的奖惩信息
    getEcs() {
      //发送查询请求,传入分页参数和条件搜索项
      this.getRequest("/empec/getEmpEcs", {
        pageNum: this.page.pageNum,
        pageSize: this.page.pageSize,
        searchName: this.searchName,
      }).then((resp) => {
        //有回复的话,给empEcs赋值,给分页参数赋值
        if (resp) {
          this.empEcs = resp.data.list;
          this.page.pageNum = resp.data.pageNum;
          this.page.pageSize = resp.data.pageSize;
          this.page.total = resp.data.total;
        }
      });
    },

    ////新增和修改相关方法
    //获取所有的员工信息 员工id和姓名
    getEmps() {
      this.getRequest("/empec/getEmps", {}).then((resp) => {
        //有回复的话,给options赋值,给分页参数赋值
        if (resp) {
          this.options = resp.data;
        }
      });
    },
    //添加弹框控制
    addEmpEc() {
      this.getEmps();
      this.title = "添加奖惩信息";
      this.dialogVisible = true;
    },
    //执行添加和修改奖惩
    doAddEmpEc() {
      if (this.empEc.id) {
        this.postRequest("/empec/updateEmpEc", this.empEc).then((resp) => {
          if (resp) {
            //修改完成后重新加载页面，情况一下数据， 也可以用this.$router.go(0)
            location.reload();
          }
        });
      } else {
        this.postRequest("/empec/addEmpEc", this.empEc).then((resp) => {
          if (resp) {
            //添加完成后重新加载页面，情况一下数据， 也可以用this.$router.go(0)
            location.reload();
          }
        });
      }
    },
    //控制修改奖惩弹框
    showEditEmpEcView(row) {
      //获取所有员工以供选择
      this.getEmps();
      this.title = "修改奖惩信息";
      this.empEc = row;
      this.dialogVisible = true;
    },
    //清空修改框的内容
    clearEmpEc() {
      this.dialogVisible = false;
      this.empEc = {};
    },

    //删除数据绑定事件
    deleteEmpEc(row) {
      this.$confirm("此操作将永久删除该奖惩信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.deleteRequest("/empec/deleteEmpEcById", { id: row.id }).then(
            () => {
              this.getEcs();
              // if (resp) {
              //   this.$message({
              //     type: "success",
              //     message: "删除成功!",
              //   });
              // }
            }
          );
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
  },
  //页面加载 获取所有的用户
  created: function () {
    this.getEcs();
  },
};
</script>

<style lang="less" scoped>
.el-table {
  // /deep/ th {
  //   padding: 10;
  // }

  // /deep/ td {
  //   //大于0的时候不管多少都一个效果 ？？？
  //   padding: 10;
  // }

  /deep/ .warning-row {
    //大于0的时候不管多少都一个效果 ？？？
    // padding: 10; background: oldlace;
    background: oldlace;
  }

  /deep/ .success-row {
    //大于0的时候不管多少都一个效果 ？？？
    // padding: 10;
    background: #e2f7d6;
  }
}
</style>