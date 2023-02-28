<template>
  <div>
    <div style="margin-top: 20px">
      <!-- accordion 允许展开1个菜单 手风琴模式 -->
      <el-collapse accordion>
        <!-- <template v-for="(empMove, key) in empMoves">
          <el-collapse-item :key="key" :disabled="empMove.length == 0"> -->
        <div v-for="(empMove, key) in empMoves" :key="key">
          <el-collapse-item :disabled="empMove.length == 0">
            <template slot="title" class="collapse-title">
              <!-- 标题右侧信息 -->
              <!-- <div class="collapse-title-length">
                <i class="el-icon-delete" style="color:hotpink" @click="change(empMove)"></i>
              </div> -->
              <!-- 左侧以员工名字为标题 -->
              <div>{{ empMove.empName }}</div>
            </template>
            <el-descriptions title="调动前信息">
              <el-descriptions-item label="部门名称">{{
                empMove.beforeDepName
                }}</el-descriptions-item>
              <el-descriptions-item label="岗位名称">{{
                empMove.beforeJobName
                }}</el-descriptions-item>
              <el-descriptions-item label="调动日期">{{
                empMove.removeDate
                }}</el-descriptions-item>
              <el-descriptions-item label="调动原因">
                {{ empMove.reason }}
              </el-descriptions-item>
              <el-descriptions-item label="备注">
                {{ empMove.remark }}
              </el-descriptions-item>
            </el-descriptions>
            <!-- 分割线 -->
            <el-divider></el-divider>
            <el-descriptions title="调动后信息">
              <el-descriptions-item label="部门名称">{{
                empMove.afterDepName
                }}</el-descriptions-item>
              <el-descriptions-item label="岗位名称">{{
                empMove.afterJobName
                }}</el-descriptions-item>
              <el-descriptions-item label="删除">
                <i class="el-icon-delete" style="color: hotpink; margin-top: 4px; cursor: pointer"
                  @click="change(empMove)"></i>
              </el-descriptions-item>
            </el-descriptions>
          </el-collapse-item>
        </div>
        <!-- 最后一个 添加员工调动 -->
        <el-collapse-item @click.native="addEmpRemove">
          <template slot="title" class="collapse-title">
            <div style="width: 100%">点击添加</div>
          </template>
          <div style="border-radius: 5px; box-sizing: border-box; padding: 5px">
            <el-row>
              <el-col :span="4">
                员工:
                <el-select v-model="empMove.eid" placeholder="根据姓名搜索" size="mini" style="width: 150px" clearable
                  filterable>
                  <el-option v-for="item in emps" :key="item.id" :label="item.name" :value="item.id">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="4">
                新部门:
                <el-select v-model="empMove.afterDepId" placeholder="根据部门搜索" size="mini" style="width: 150px" clearable
                  filterable>
                  <el-option v-for="item in deps" :key="item.id" :label="item.name" :value="item.id">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="4">
                新职位:
                <el-select v-model="empMove.afterJobId" placeholder="根据职位搜索" size="mini" style="width: 150px" clearable
                  filterable>
                  <el-option v-for="item in poss" :key="item.id" :label="item.name" :value="item.id">
                  </el-option>
                </el-select>
              </el-col>
            </el-row>
            <el-row style="margin-top: 10px">
              <el-col :span="1"> 调动原因: </el-col>
              <el-col :span="4">
                <el-input size="mini" placeholder="请输入内容" type="textarea" autosize v-model="empMove.reason">
                </el-input>
              </el-col>
              <el-col :span="1" style="margin-left: 50px"> 备注信息: </el-col>
              <el-col :span="4">
                <el-input size="mini" placeholder="请输入内容" type="textarea" autosize v-model="empMove.remark">
                </el-input>
              </el-col>

              <el-col :span="5" style="margin-left: 30px">
                <el-button size="mini" type="primary" @click="savaEmpRemove(empMove)">确认添加</el-button>
              </el-col>
            </el-row>
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      getInfoFlag: false,
      empMove: {},
      emps: [
        {
          id: "1",
          name: "123",
        },
      ],
      deps: [
        {
          id: "1",
          name: "123",
        },
      ],
      poss: [
        {
          id: "1",
          name: "123",
        },
      ],
      empMoves: [
        {
          empName: "张三",
          beforeDepName: "研发部",
          beforeJobName: "JAVA开发",
          afterDepName: "研发部",
          afterJobName: "软件测试",
          moveReason: "个人原因",
          remark: "",
          moveDate: "2022-6-8",
        },
        {
          empName: "李四",
          beforeDepName: "研发部",
          beforeJobName: "JAVA开发",
          afterDepName: "研发部",
          afterJobName: "软件测试",
          moveReason: "个人原因",
          remark: "",
          moveDate: "2022-6-8",
        },
      ],
    };
  },
  methods: {
    //点击添加触发的事件，获取可选择项信息
    addEmpRemove() {
      if (this.getInfoFlag == false) {
        this.getInfo();
      }
    },
    //获取可选择项信息
    getInfo() {
      this.getEmps();
      this.getDeps();
      this.getPoss();
      this.getInfoFlag = true;
    },
    //获取所有员工
    getEmps() {
      this.getRequest("/getEmpNameAndId", {}).then((resp) => {
        if (resp) {
          this.emps = resp.data;
        }
      });
    },
    //获取所有部门
    getDeps() {
      this.getRequest("/department/getDepNameAndId", {}).then((resp) => {
        if (resp) {
          this.deps = resp.data;
        }
      });
    },
    //获取所有职位
    getPoss() {
      this.getRequest("/position/getPosNameAndId", {}).then((resp) => {
        if (resp) {
          this.poss = resp.data;
        }
      });
    },
    //删除调动信息
    change(empmove) {
      this.$confirm("此操作将删除该条调动信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.deleteRequest("/empremove/deleteEmpRemove", {
          empRemoveId: empmove.id,
        }).then((resp) => {
          if (resp) {
            // this.getEmpRemoves();
            location.reload();
          }
        });
      });
    },
    //添加调动信息
    savaEmpRemove(empMove) {
      this.postRequest("/empremove/doAddEmpMove", empMove).then((resp) => {
        if (resp) {
          //添加完成后重新加载页面，情况一下数据， 也可以用this.$router.go(0)
          this.getEmpRemoves();
        }
      });
    },
    //获取所有的调度信息
    getEmpRemoves() {
      //发送查询请求,传入分页参数和条件搜索项
      this.getRequest("/empremove/getEmpRemoves", {}).then((resp) => {
        //有回复的话,给empMoves赋值,给分页参数赋值
        if (resp) {
          this.empMoves = resp.data;
        }
      });
    },
  },
  //页面加载 获取所有的用户
  created: function () {
    this.getEmpRemoves();
  },
};
</script>

<style lang="less" scoped>
// el-collapse-item标题的样式
.collapse-title {
  display: flex;
  &-length {
    margin-left: auto;
    margin-right: 14px;
  }
}
/deep/ .el-collapse-item__content {
  padding: 0;
}
/deep/ .el-collapse-item__arrow {
  margin: 4px 4px 4px 12px;
}
/deep/ .el-collapse-item__header {
  color: #409eff;
  height: 40px;
  display: flex;
  flex-direction: row-reverse;
  justify-content: flex-end;
}
</style>