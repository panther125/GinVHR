<template>
  <div style="margin-top: 20px">
    <div class="hr-container">
      <!-- 遍历所有的trains信息 每个train信息都是一个el-card -->
      <el-card class="hr-card" v-for="(train, index) in trains" :key="index">
        <!--名字和删除图标  -->
        <div slot="header" class="clearfix" style="text-align: center">
          <span>{{ train.name }}</span>

          <el-button
            style="float: right; padding: 3px 0; color: #e30007"
            type="text"
            icon="el-icon-delete"
            @click="deleteTrain(train.id)"
          ></el-button>
        </div>
        <!-- 头像 展示报名人数 el-badge-->
        <div
          style="display: flex; justify-content: center; margin-bottom: 10px"
        >
          <el-badge :value="train.total" class="item1">
            <img
              src="../images/java1.png"
              alt=""
              style="width: 60px; height: 60px; border-radius: 60px"
            />
          </el-badge>
        </div>
        <!-- 用户信息 -->
        <div class="userinfo-container">
          <div style="font-size: 18px; color: #e30007">
            培训日期：{{ train.trainDate }}
          </div>

          <div style="font-size: 18px; color: #e30007">
            培训内容：{{ train.trainContent }}
          </div>

          <div style="font-size: 18px; color: #e30007">
            备注：{{ train.remark }}
          </div>
          <!-- 热度 el-rate评分图  show-score展示分数 score-template="{value}"分数数据绑定 -->
          <div
            style="
              display: flex;
              font-size: 18px;
              margin-top: 2px;
              color: #e30007;
            "
          >
            热度：
            <el-rate
              v-model="train.hot"
              disabled
              show-score
              text-color="#ff9900"
              score-template="{value}"
              style="margin-top: 3px"
            >
            </el-rate>
          </div>
          <div style="font-size: 18px; margin-top: 3px; color: #e30007">
            培训人员：
            <!-- 弹框为表格 固定宽度，超过宽度增加滚动条 需要在app.vue中增加样式
            在引用该组件的.vue中加这个样式也可以 但是不能加上 scoped 因为你这个组件的样式是全局的
            .el-popover{
              height: 300px; 
              overflow: auto;
            } -->
            <el-popover
              placement="right"
              width="320"
              trigger="click"
              class="pop-up"
            >
              <el-table :data="empData">
                <el-table-column
                  width="100"
                  property="empName"
                  label="姓名"
                ></el-table-column>
                <el-table-column
                  width="130"
                  property="depName"
                  label="部门"
                ></el-table-column>
                <!-- 删除图标展示 el-tooltip：文字提示-->
                <el-table-column width="70" label="操作">
                  <template slot-scope="scope">
                    <el-tooltip
                      content="移除该员工"
                      placement="right"
                      effect="light"
                    >
                      <el-button
                        style="padding: 3px 0; color: #e30007"
                        type="text"
                        icon="el-icon-delete"
                        @click="deleteTrainEmp(train.id, scope.row)"
                      ></el-button>
                    </el-tooltip>
                  </template>
                </el-table-column>
              </el-table>
              <!-- 点击获取报名的员工信息 -->
              <el-button
                slot="reference"
                size="mini"
                type="primary"
                @click="getTrainEmps(train.id)"
                >人员列表</el-button
              >
            </el-popover>
            <!-- <el-button slot="reference" size="mini" type="success" style="margin-left:15px">添加培训人员 -->
            <!-- <div style="font-size:18px;margin-top: 3px;color: #e30007;">
              添加培训人员
            </div> -->
            <!-- 添加培训人员弹框 多级选择 collapse-tags：选择多个的时候采用+数字的方法 而不是扩大选择框 -->
            <!-- @visible-change="xiala($event, train.id, index)" 弹出弹框和收回弹框触发事件 -->
            <el-cascader
              ref="myCascader"
              :show-all-levels="false"
              :options="options"
              :props="optionProps"
              collapse-tags
              clearable
              placeholder="添加培训人员"
              size="small"
              style="width: 120px; margin-left: 10px"
              @visible-change="xiala($event, train.id, index)"
              v-model="train.selectId"
            >
            </el-cascader>
            <!-- </el-button> -->
          </div>
        </div>
      </el-card>
      <!-- 最后一个card 点击添加hr信息 -->
      <!-- style="min-height: 200px" 自己一行时保证card的高度 不会变矮-->
      <el-card class="hr-card" style="min-height: 300px">
        <div
          style="
            display: flex;
            justify-content: center;
            margin-top: 40%;
            cursor: pointer;
          "
        >
          <el-tooltip
            class="item"
            effect="dark"
            content="添加新的培训计划"
            placement="right-end"
          >
            <i class="el-icon-circle-plus-outline" @click="addEmpTrain"></i>
          </el-tooltip>
        </div>
      </el-card>
    </div>
    <!-- 新增培训的弹框 -->
    <el-dialog :visible.sync="dialogVisible">
      <el-form :model="train" :rules="rules" ref="train" label-width="100px">
        <el-form-item label="培训名称" prop="name">
          <el-input v-model="train.name"></el-input>
        </el-form-item>
        <!-- <el-form-item label="参加员工" prop="region">
          <el-cascader ref="myCascader" :show-all-levels="false" :options="options" :props="optionProps" collapse-tags
            clearable placeholder="添加培训人员"  
            @visible-change="xiala($event, train.id)" v-model="selectId">
          </el-cascader>
        </el-form-item> -->
        <el-form-item label="培训日期" required>
          <el-form-item prop="trainDate">
            <el-date-picker
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd"
              v-model="train.trainDate"
            >
            </el-date-picker>
          </el-form-item>
        </el-form-item>
        <el-form-item label="培训内容" prop="trainContent">
          <el-input v-model="train.trainContent"></el-input>
        </el-form-item>
        <el-form-item label="热度评分" prop="hot">
          <el-input
            placeholder="最高5分;例如:3.2"
            v-model="train.hot"
            clearable
            style="width: 200px"
          >
          </el-input>
        </el-form-item>
        <!-- <el-form-item label="活动性质" prop="type">
          <el-checkbox-group v-model="ruleForm.type">
            <el-checkbox label="美食/餐厅线上活动" name="type"></el-checkbox>
            <el-checkbox label="地推活动" name="type"></el-checkbox>
            <el-checkbox label="线下主题活动" name="type"></el-checkbox>
            <el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="特殊资源" prop="resource">
          <el-radio-group v-model="ruleForm.resource">
            <el-radio label="线上品牌商赞助"></el-radio>
            <el-radio label="线下场地免费"></el-radio>
          </el-radio-group>
        </el-form-item> -->
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="train.remark"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('train')"
            >立即创建</el-button
          >
          <el-button @click="resetForm('train')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  methods: {
    //添加培训控制弹框
    addEmpTrain() {
      this.dialogVisible = true;
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    submitForm(train) {
      this.$refs[train].validate((valid) => {
        if (valid) {
          // console.log(this.train)
          // alert('submit!');
          this.postRequest("/emptrain/doAddTrain", this.train).then((resp) => {
            if (resp) {
              //添加完成后重新加载页面，情况一下数据， 也可以用this.$router.go(0)
              this.dialogVisible = false;
              this.gettrains();
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    // 删除培训
    deleteTrain(trainId) {
      this.$confirm("此操作将永久删除该培训信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.deleteRequest("/emptrain/deleteTrain", {
            trainId: trainId,
          }).then((resp) => {
            if (resp) {
              //重新获取所有的培训
              this.gettrains();
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },

    //人员列表中删除培训的员工
    deleteTrainEmp(trainId, row) {
      // alert(trainId)
      // alert(row.empId);
      this.deleteRequest("/emptrain/deleteTrainEmp", {
        trainId: trainId,
        empId: row.empId,
      }).then((resp) => {
        if (resp) {
          //重新获取参与的员工
          this.getTrainEmps(trainId);
          //重新获取所有的培训
          this.gettrains();
        }
      });
    },
    // 弹出下拉菜单时触发的事件 需要获取可参加培训的人员
    //弹回下拉菜单时触发事件 更新培训人员 和总数量
    xiala(type, id, index) {
      // alert(id);
      // 弹出菜单
      if (type) {
        //获取可参加培训的人员 先获取部门 再获取该部门员工
        this.getOptions(id);
      } else {
        //收回弹框 更新参与培训的人员及总人数
        //获取选择的节点 true：只获取叶子节点 也就是员工
        let node = this.$refs.myCascader[index].getCheckedNodes(true); // 获取选中节点对象
        // console.log(node[0].data.id)
        //获取已选择节点的id
        node.forEach((item) => {
          this.train.selectId.push(item.data.id);
        });
        // console.log(this.train.selectId)
        // 传入员工id 和该培训的id
        this.doAddTrainEmps(this.train.selectId, id);
      }
    },
    //添加参与培训的员工
    doAddTrainEmps(selectId, trainId) {
      // console.log(selectId);

      // selectId.forEach((item) => {
      //删除数组第一个元素
      //     item.shift();
      // });
      //将数组中的数组变为number
      // for (var i = 0; i < selectId.length;i++){
      //   selectId[i]=selectId[i][0]
      // }
      this.postRequest("/emptrain/doAddTrainEmps", {
        selectId: selectId,
        trainId: trainId,
      }).then((resp) => {
        if (resp) {
          //添加完成后重新加载页面，情况一下数据， 也可以用this.$router.go(0)
          this.gettrains();
        }
      });
    },
    //获取所有的培训
    gettrains() {
      //发送查询请求,传入分页参数和条件搜索项
      this.getRequest("/emptrain/getEmpTrains", {
        searchName: this.searchName,
      }).then((resp) => {
        //有回复的话,给empEcs赋值,给分页参数赋值

        if (resp) {
          this.trains = resp.data;
        }
      });
    },
    //点击人员列时表获取所有培训的员工
    getTrainEmps(id) {
      // alert(id);
      this.getRequest("/emptrain/getTrainEmps", {
        trainId: id,
      }).then((resp) => {
        //有回复的话,给empEcs赋值,给分页参数赋值

        if (resp) {
          this.empData = resp.data;
        }
      });
    },
    //点击添加培训人员时获取所有可参与培训的部门中的员工
    getOptions(id) {
      this.getRequest("/emptrain/getOptions", {
        trainId: id,
      }).then((resp) => {
        //有回复的话,给empEcs赋值,给分页参数赋值
        if (resp) {
          this.options = resp.data;
        }
      });
    },
  },
  data() {
    return {
      //// 添加培训相关
      rules: {
        name: [{ required: true, message: "请输入活动名称", trigger: "blur" }],
        trainDate: [{ required: true, message: "请选择日期", trigger: "blur" }],
        trainContent: [
          { required: true, message: "请输入培训内容", trigger: "blur" },
        ],
        hot: [
          { required: true, message: "请选择活动资源", trigger: "blur" },
          {
            pattern: /^([0-4]\.?\d{0,1}|5)$/,
            message: "评分0-5,且最多1位小数",
            trigger: "blur",
          }, //0-5 可包含以为小数
        ],
        remark: [{ required: true, message: "请输入备注", trigger: "blur" }],
      },

      ruleForm: {
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        type: [],
        resource: "",
        desc: "",
      },
      dialogVisible: false,
      //// 培训内容处理相关
      // selectId:[], //选中的id
      //添加培训人员时映射关系
      optionProps: {
        value: "id",
        label: "name",
        multiple: true, //可多选
        children: "children",
        // checkStrictly: true, //可以选择任意一级
        expandTrigger: "hover", //鼠标放上触发
      },
      //参加培训的员工
      empData: [
        {
          name: "123",
          dep: "123",
        },
        {
          name: "123",
          dep: "123",
        },
        {
          name: "123",
          dep: "123",
        },
        {
          name: "123",
          dep: "123",
        },
        {
          name: "123",
          dep: "123",
        },
        {
          name: "123",
          dep: "123",
        },
        {
          name: "123",
          dep: "123",
        },
        {
          name: "123",
          dep: "123",
        },
        {
          name: "123",
          dep: "123",
        },
        {
          name: "123",
          dep: "123",
        },
        {
          name: "123",
          dep: "123",
        },
        {
          name: "123",
          dep: "123",
        },
        {
          name: "123",
          dep: "123",
        },
      ],
      train: {
        selectId: [],
      },
      //添加培训人员菜单项
      options: [
        {
          value: 1,
          label: "东南",
          children: [
            {
              value: 2,
              label: "上海",
              children: [
                { value: 3, label: "普陀" },
                { value: 4, label: "黄埔" },
                { value: 5, label: "徐汇" },
              ],
            },
          ],
        },
      ],
      //所有的培训
      trains: [
        {
          name: "java培训",
          trainDate: "2022-6-6",
          trainContent: "zha",
          remark: "hahah",
        },
      ],
    };
  },
  //页面加载 获取所有的用户
  created: function () {
    this.gettrains();
  },
};
</script>

<style  lang="less">
// card父div的样式
.hr-container {
  margin-left: 20px;
  margin-top: 10px;
  display: flex;
  /* 必要的时候可以拆行 */
  flex-wrap: wrap;
  /* 盒子平均分配居中布局 但是最后一行盒子不满的话 也会居中 */
  /* justify-content: space-around; */
}
/* 采用固定盒子 也就是card加左右边距margin */
.hr-card {
  width: 350px;
  margin: 20px;
}
/* 显示报名数量的样式 */
.item1 {
  margin-top: 10px;
  margin-right: 40px;
}
</style>