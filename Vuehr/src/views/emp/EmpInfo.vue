<template>
  <div>
    <div
      style="display: flex; justify-content: space-between; margin-top: 10px"
    >
      <!-- 搜索和高级搜索 -->
      <div>
        <!-- 输入框 可清除的 绑定的数据为v-model="search.employeeName" 回车搜索@keydown.enter.native="getEmps -->
        <!-- :disabled="showAdvanceSearchView" 如果是高级搜索的话此搜索不可用 -->
        <el-input
          placeholder="请输入员工名进行搜索，可以直接回车搜索..."
          prefix-icon="el-icon-search"
          clearable
          style="width: 350px; margin-right: 10px"
          v-model="search.employeeName"
          @keydown.enter.native="getEmps"
          :disabled="showAdvanceSearchView"
        ></el-input>
        <el-button
          icon="el-icon-search"
          type="primary"
          @click="getEmps"
          :disabled="showAdvanceSearchView"
        >
          搜索
        </el-button>
        <!-- 点击触发两个事件 1.获取初始数据 2.展示搜索页面  -->
        <el-button
          type="primary"
          @click="
            init_data();
            handSearchView();
          "
        >
          <i
            :class="
              showAdvanceSearchView
                ? 'fa fa-angle-double-up'
                : 'fa fa-angle-double-down'
            "
            aria-hidden="true"
          ></i>
          高级搜索
        </el-button>
      </div>
      <!-- 导入导出添加用户 -->
      <div>
        <el-upload
          :show-file-list="false"
          :disabled="importDataDisabled"
          :before-upload="beforeUpload"
          :on-success="onSuccess"
          :on-error="onError"
          style="display: inline-flex; margin-right: 8px"
          action="/vhr/importEmpData"
        >
          <el-button
            :disabled="importDataDisabled"
            type="success"
            icon="el-icon-upload2"
          >
            {{ importDataBtnText }}
          </el-button>
        </el-upload>
        <el-button type="success" @click="exportData" icon="el-icon-download">
          导出数据
        </el-button>
        <el-button type="primary" icon="el-icon-plus" @click="showAddEmpView">
          添加用户
        </el-button>
      </div>
    </div>

    <!-- 高级搜索部分 -->
    <!-- transaction是动画效果 -->
    <!-- solid（实线） -->
    <!-- box-sizing: border-box就是将border和padding数值包含在width和height之内，这样的好处就是修改border和padding数值盒子的大小不变。 -->
    <transition name="slide-fade">
      <div
        v-show="showAdvanceSearchView"
        style="
          border: 1px solid #409eff;
          border-radius: 5px;
          box-sizing: border-box;
          padding: 5px;
          margin: 10px 0px;
        "
      >
        <!--el-row 行格式  el-col行内容 col可以有很多个 但是span的总值为24 不够的话可以设置:offset -->
        <el-row>
          <el-col :span="5">
            政治面貌:
            <el-select
              v-model="search.politicId"
              placeholder="政治面貌"
              size="mini"
              style="width: 130px"
              clearable
            >
              <el-option
                v-for="item in politicsstatus"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="4">
            民族:
            <el-select
              v-model="search.nationId"
              placeholder="民族"
              size="mini"
              style="width: 130px"
              clearable
            >
              <el-option
                v-for="item in nations"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="4">
            职位:
            <el-select
              v-model="search.posId"
              placeholder="职位"
              size="mini"
              style="width: 130px"
              clearable
            >
              <el-option
                v-for="item in positions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="4">
            职称:
            <el-select
              v-model="search.jobLevelId"
              placeholder="职称"
              size="mini"
              style="width: 130px"
              clearable
            >
              <el-option
                v-for="item in joblevels"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-col>
          <!-- 按钮组 -->
          <el-col :span="7">
            聘用形式:
            <el-radio-group v-model="search.engageForm">
              <el-radio label="劳动合同">劳动合同</el-radio>
              <el-radio label="劳务合同">劳务合同</el-radio>
            </el-radio-group>
          </el-col>
        </el-row>
        <el-row style="margin-top: 10px">
          <el-col :span="5">
            所属部门:
            <el-cascader
              size="mini"
              v-model="depId"
              :options="options"
              :props="optionProps"
              :show-all-levels="false"
              :checkStrictly="true"
              clearable
            >
            </el-cascader>
          </el-col>
          <el-col :span="9">
            入职日期:
            <el-date-picker
              v-model="search.start"
              type="date"
              placeholder="选择日期"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
              size="mini"
            >
            </el-date-picker>
            至:
            <el-date-picker
              v-model="search.end"
              type="date"
              placeholder="选择日期"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
              size="mini"
            >
            </el-date-picker>
          </el-col>

          <!-- 按钮组 -->
          <el-col :span="5">
            在职状态:
            <el-radio-group v-model="search.workState">
              <el-radio label="在职">在职</el-radio>
              <el-radio label="离职">离职</el-radio>
            </el-radio-group>
          </el-col>

          <el-col :span="5">
            <el-button size="mini" @click="clearSearch()">取消</el-button>
            <el-button
              size="mini"
              icon="el-icon-search"
              type="primary"
              @click="getEmps()"
              >搜索</el-button
            >
          </el-col>
        </el-row>
      </div>
    </transition>

    <!-- 表格部分 -->
    <div style="margin-top: 10px">
      <!-- 绑定数据为emps :row-class-name="tableRowClassName"指定每行的类名 显示不同颜色背景 -->
      <el-table
        :data="emps"
        border
        style="width: 100%"
        v-loading="loading"
        element-loading-text="正在加载..."
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)"
        :row-class-name="tableRowClassName"
      >
        <!-- 名字这一列固定 不随滚动条滚动 -->
        <el-table-column
          property="name"
          fixed
          label="姓名"
          width="100"
          height="50px"
        >
        </el-table-column>
        <el-table-column property="workID" label="工号" width="100">
        </el-table-column>
        <el-table-column property="workState" label="在职状态" width="100">
        </el-table-column>
        <el-table-column property="gender" label="性别" width="60">
        </el-table-column>
        <el-table-column property="birthday" label="出生日期" width="120">
        </el-table-column>
        <el-table-column property="idCard" label="身份证号码" width="170">
        </el-table-column>
        <el-table-column property="wedlock" label="婚姻状况" width="80">
        </el-table-column>
        <el-table-column property="nation.name" label="民族" width="70">
        </el-table-column>
        <el-table-column property="nativePlace" label="籍贯" width="80">
        </el-table-column>
        <el-table-column
          property="politicsstatus.name"
          label="政治面貌"
          width="120"
        >
        </el-table-column>
        <el-table-column property="email" label="电子邮件" width="170">
        </el-table-column>
        <el-table-column property="phone" label="电话号码" width="120">
        </el-table-column>
        <el-table-column property="address" label="联系地址" width="190">
        </el-table-column>
        <el-table-column
          property="department.name"
          label="所属部门"
          width="100"
        >
        </el-table-column>
        <el-table-column
          property="position.name"
          label="职位"
          width="100"
        >
        </el-table-column>
        <el-table-column
          property="jobLevel.name"
          label="职称"
          width="100"
        >
        </el-table-column>
        <el-table-column property="engageForm" label="聘用形式" width="100">
        </el-table-column>
        <el-table-column property="tiptopDegree" label="最高学历" width="100">
        </el-table-column>
        <el-table-column property="specialty" label="专业" width="150">
        </el-table-column>
        <el-table-column property="school" label="毕业院校" width="140">
        </el-table-column>
        <el-table-column
          property="beginDate"
          label="入职日期"
          width="110"
          sortable
        >
        </el-table-column>
        <el-table-column property="conversionTime" label="转正日期" width="100">
        </el-table-column>
        <el-table-column
          property="beginContract"
          label="合同起始日期"
          width="110"
        >
        </el-table-column>
        <el-table-column
          property="endContract"
          label="合同终止日期"
          width="110"
        >
        </el-table-column>
        <el-table-column
          property="contractTerm"
          label="合同期限/年"
          width="100"
        >
        </el-table-column>
        <!-- 操作这一列固定在最右边 -->
        <!-- slot-scope="scope"  很关键 可以取出每行的数据 scope.row -->
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <el-button
              @click="showEditEmpView(scope.row)"
              style="padding: 4px"
              size="mini"
              >编辑
            </el-button>
            <el-button
              type="danger"
              @click="deleteEmp(scope.row)"
              size="mini"
              style="padding: 3px"
              >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页工具展示-->
    <div class="block" style="float: right; margin: 10px">
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

    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="80%"
      @close="clearEmp"
    >
      <div>
        <el-form :model="emp" :rules="rules" ref="empForm">
          <!-- 第一行 -->
          <el-row>
            <el-col :span="6">
              <el-form-item label="姓名:" prop="name">
                <el-input
                  size="mini"
                  style="width: 150px"
                  prefix-icon="el-icon-edit"
                  v-model="emp.name"
                  placeholder="请输入员工姓名"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="性别:" prop="gender">
                <el-radio-group v-model="emp.gender" style="margin-top: 13px">
                  <el-radio label="男">男</el-radio>
                  <el-radio label="女">女</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="出生日期:" prop="birthday">
                <el-date-picker
                  v-model="emp.birthday"
                  size="mini"
                  type="date"
                  value-format="yyyy-MM-dd"
                  style="width: 150px"
                  placeholder="出生日期"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="政治面貌:" prop="politicId">
                <el-select
                  v-model="emp.politicId"
                  placeholder="政治面貌"
                  size="mini"
                  style="width: 200px"
                >
                  <el-option
                    v-for="item in politicsstatus"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第二行 -->
          <el-row>
            <el-col :span="6">
              <el-form-item label="民族:" prop="nationId">
                <el-select
                  v-model="emp.nationId"
                  placeholder="民族"
                  size="mini"
                  style="width: 150px"
                >
                  <el-option
                    v-for="item in nations"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="籍贯:" prop="nativePlace">
                <el-input
                  size="mini"
                  style="width: 120px"
                  prefix-icon="el-icon-edit"
                  v-model="emp.nativePlace"
                  placeholder="请输入籍贯"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="电子邮箱:" prop="email">
                <el-input
                  size="mini"
                  style="width: 150px"
                  prefix-icon="el-icon-message"
                  v-model="emp.email"
                  placeholder="请输入电子邮箱"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="联系地址:" prop="address">
                <el-input
                  size="mini"
                  style="width: 200px"
                  prefix-icon="el-icon-edit"
                  v-model="emp.address"
                  placeholder="请输入联系地址"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第三行 -->
          <el-row>
            <el-col :span="6">
              <el-form-item label="职位:" prop="posId">
                <el-select
                  v-model="emp.posId"
                  placeholder="职位"
                  size="mini"
                  style="width: 150px"
                >
                  <el-option
                    v-for="item in positions"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="职称:" prop="jobLevelId">
                <el-select
                  v-model="emp.jobLevelId"
                  placeholder="职称"
                  size="mini"
                  style="width: 150px"
                >
                  <el-option
                    v-for="item in joblevels"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="所属部门:" prop="depId">
                <el-cascader
                  size="mini"
                  v-model="depId"
                  :options="options"
                  :props="optionProps_a"
                  :show-all-levels="false"
                  style="margin-top: 5px"
                  :placeholder="depId2"
                ></el-cascader>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="电话号码:" prop="phone">
                <el-input
                  size="mini"
                  style="width: 200px"
                  prefix-icon="el-icon-phone"
                  v-model="emp.phone"
                  placeholder="电话号码"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第四行 -->
          <el-row>
            <el-col :span="6">
              <el-form-item label="工号:" prop="workId">
                <el-input
                  size="mini"
                  style="width: 150px"
                  prefix-icon="el-icon-edit"
                  v-model="emp.workId"
                  placeholder="工号"
                  disabled
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="学历:" prop="tiptopDegree">
                <el-select
                  v-model="emp.tiptopDegree"
                  placeholder="学历"
                  size="mini"
                  style="width: 150px"
                >
                  <el-option
                    v-for="item in tiptopDegrees"
                    :key="item"
                    :label="item"
                    :value="item"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="毕业院校:" prop="school">
                <el-input
                  size="mini"
                  style="width: 150px"
                  prefix-icon="el-icon-edit"
                  v-model="emp.school"
                  placeholder="毕业院校名称"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="专业名称:" prop="specialty">
                <el-input
                  size="mini"
                  style="width: 200px"
                  prefix-icon="el-icon-edit"
                  v-model="emp.specialty"
                  placeholder="请输入专业名称"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第五行 -->
          <el-row>
            <el-col :span="6">
              <el-form-item label="入职日期:" prop="beginDate">
                <el-date-picker
                  v-model="emp.beginDate"
                  size="mini"
                  type="date"
                  value-format="yyyy-MM-dd"
                  style="width: 130px"
                  placeholder="入职日期"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="转正日期:" prop="conversionTime">
                <el-date-picker
                  v-model="emp.conversionTime"
                  size="mini"
                  type="date"
                  value-format="yyyy-MM-dd"
                  style="width: 130px"
                  placeholder="转正日期"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="合同起始日期:" prop="beginContract">
                <el-date-picker
                  v-model="emp.beginContract"
                  size="mini"
                  type="date"
                  value-format="yyyy-MM-dd"
                  style="width: 130px"
                  placeholder="合同起始日期"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="合同终止日期:" prop="endContract">
                <el-date-picker
                  v-model="emp.endContract"
                  size="mini"
                  type="date"
                  value-format="yyyy-MM-dd"
                  style="width: 150px"
                  placeholder="合同终止日期"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第六行 -->
          <el-row>
            <el-col :span="8">
              <el-form-item label="身份证号码:" prop="idCard">
                <el-input
                  size="mini"
                  style="width: 180px"
                  prefix-icon="el-icon-edit"
                  v-model="emp.idCard"
                  placeholder="请输入身份证号码"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="聘用形式:" prop="engageForm">
                <el-radio-group
                  v-model="emp.engageForm"
                  style="margin-top: 13px"
                >
                  <el-radio label="劳动合同">劳动合同</el-radio>
                  <el-radio label="劳务合同">劳务合同</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="婚姻状况:" prop="wedlock">
                <el-radio-group v-model="emp.wedlock" style="margin-top: 13px">
                  <el-radio label="已婚">已婚</el-radio>
                  <el-radio label="未婚">未婚</el-radio>
                  <el-radio label="离异">离异</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="clearEmp">取 消</el-button>
        <el-button type="primary" @click="doAddEmp">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  methods: {
    doAddEmp() {
      //如果有id说明是修改
      if (this.emp.id) {
        console.log(this.emp);
        this.$refs["empForm"].validate((valid) => {
          if (valid) {
            if (this.depId.length > 0) {
              this.emp.departmentId = this.depId[this.depId.length - 1];
            }
            this.putRequest("/updateEmp", this.emp).then((resp) => {
              if (resp) {
                // this.dialogVisible = false;
                //添加完成后重新加载页面，清空一下数据， 也可以用this.$router.go(0)
                location.reload();
              }
            });
          }
        });
      } else {
        this.$refs["empForm"].validate((valid) => {
          if (valid) {
            if (this.depId.length > 0) {
              this.emp.departmentId = this.depId[this.depId.length - 1];
            }
            this.postRequest("/addEmp", this.emp).then((resp) => {
              if (resp) {
                // this.dialogVisible = false;
                //添加完成后重新加载页面，情况一下数据， 也可以用this.$router.go(0)
                location.reload();
              }
            });
          }
        });
      }
    },

    handSearchView() {
      this.showAdvanceSearchView = !this.showAdvanceSearchView;
    },
    //高级查询时初始化可选数据
    init_data() {
      //获取所有的职称
      if (this.joblevels == undefined || this.joblevels.length <= 0) {
        this.getRequest("/employee/basic/joblevels", {}).then((resp) => {
          if (resp) {
            this.joblevels = resp;
          }
        });
      }
      //获取所有政治面貌
      if (this.politicsstatus == undefined || this.politicsstatus.length <= 0) {
        this.getRequest("/employee/basic/politicsstatus", {}).then(
          (resp) => {
            if (resp) {
              this.politicsstatus = resp;
            }
          }
        );
      }
      //获取所有民族
      if (this.nations == undefined || this.nations.length <= 0) {
        this.getRequest("/employee/basic/nations", {}).then((resp) => {
          if (resp) {
            this.nations = resp;
          }
        });
      }
      //获取所有的职位
      if (this.positions == undefined || this.positions.length <= 0) {
        this.getRequest("/employee/basic/positions", {}).then((resp) => {
          if (resp) {
            this.positions = resp;
          }
        });
      }
      //获取所有的部门
      if (this.departments == undefined || this.departments.length <= 0) {
        this.getRequest("/employee/basic/deps", {}).then((resp) => {
          if (resp) {
            //在这里给options 赋值
            this.departments = resp;
            this.options = this.departments;
            // this.options.value = this.departments.id;
            // this.options.label = "123";
            // this.options.children = this.departments.children;
            this.rewriteKey(this.options);
          }
        });
      }
    },
    //解决选择部门时el-cascader最后显示无数据的问题
    rewriteKey(val) {
      val.forEach((item) => {
        item.children = item.children.length !== 0 ? item.children : undefined;
        if (item.children) {
          this.rewriteKey(item.children);
        }
      });
    },
    //添加员工控制弹窗
    showAddEmpView() {
      // this.emptyEmp();
      this.getMaxWorkID();
      this.init_data();
      this.title = "添加员工";

      this.dialogVisible = true;
    },
    //获取最大workid 新增时用
    getMaxWorkID() {
      //获取到部门id数组的最后一个元素
      this.getRequest("/getMaxWorkID").then((resp) => {
        if (resp) {
          // this.emp.workId = resp.data;
          //强制赋值
          //所有数据都是需要受到监测的，但我们在页面中有时候涉及到组件调用或者计算比较复杂的情况下，这种监测有时候会被忽略，这个时候就需要用到vue的显式赋值了
          this.$set(this.emp, "workId", resp.data);
          // this.$set(this.emp.workId, resp.data);
          // console.log(this.emp.workId);
        }
      });
    },
    //导出数据
    exportData() {
      window.location = "http://localhost:8081/vhr/exportEmpData";
    },
    onError() {
      this.importDataBtnText = "导入数据";
      this.importDataBtnIcon = "el-icon-upload2";
      this.importDataDisabled = false;
    },
    onSuccess() {
      this.importDataBtnText = "导入数据";
      this.importDataBtnIcon = "el-icon-upload2";
      this.importDataDisabled = false;
      this.getEmps();
    },
    beforeUpload() {
      this.importDataBtnText = "正在导入";
      this.importDataBtnIcon = "el-icon-loading";
      this.importDataDisabled = true;
    },
    //处理每页显示条数
    handleSizeChange(val) {
      this.page.pageSize = val;
      this.getEmps();
      console.log(`每页 ${val} 条`);
    },
    //处理页面切换
    handleCurrentChange(val) {
      this.page.pageNum = val;
      this.getEmps();
      console.log(`当前页: ${val}`);
    },
    //编辑员工控制弹框
    showEditEmpView(row) {
      this.init_data();
      this.dialogVisible = true;
      this.emp = row;
      this.title = "编辑员工信息";
      //部门选择的初始化值
      this.depId2 = row.departmentEntity.name;
    },
    //删除
    deleteEmp(row) {
      console.log(row);
      this.$confirm("此操作将永久删除该员工信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.deleteRequest("/deleteEmpById", { empId: row.id }).then(() => {
            this.loading = false;
            this.getEmps();
            // if (resp) {
            //   this.$message({
            //     type: "success",
            //     message: "删除成功!",
            //   });
            // }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    //获取所有的员工
    getEmps() {
      this.loading = true;
      //查询时选择了部门,新增或修改时选择了部门 depId保存的是选择的部门id数组 后端只需要最后一级的部门id,在查询前进行处理
      if (this.depId.length > 0) {
        this.search.departmentId = this.depId[this.depId.length - 1];
      }
      //发送查询请求,传入分页参数和条件搜索项
      this.getRequest("/employee/basic", {
        pageNum: this.page.pageNum,
        pageSize: this.page.pageSize,
        searchParam: this.search,
      }).then((resp) => {
        //有回复的话,给emps赋值,给分页参数赋值
        this.loading = false;
        if (resp) {
          this.emps = resp.data;
          this.page.pageNum = resp.data.pageNum;
          this.page.pageSize = resp.data.pageSize;
          this.page.total = resp.total;
        }
      });
    },
    //绑定的每行的类名,根据每行的workState进行判断
    tableRowClassName({ row }) {
      if (row.workState === "离职") {
        return "success-row";
      } else return "";
    },
    //修改时退出修改框 清空emp数据 防止别处显示
    clearEmp() {
      this.emp = {};
      this.depId2 = "";
      this.dialogVisible = false;
    },
    //点击取消时,取消搜索框,清空搜索数据
    clearSearch() {
      this.search = {};
      // this.options.length=0;
      this.showAdvanceSearchView = false;
    },
  },

  data() {
    return {
      ////分页数据展示data
      emps: [], //存所有员工
      loading: false, //查询数据时的显示状态 true就转圈圈
      //分页参数 当前页 每页大小 总数
      page: {
        pageNum: 1,
        pageSize: 10,
        total: 0,
      },
      //// 高级搜索data
      showAdvanceSearchView: false, //高级搜索弹框显示
      //查询参数 姓名为基础模糊查询 其他为高级查询
      search: {
        // workState:"",     //在职状态
        // employeeName: "", //员工姓名
        // departmentId: "", //部门的id
        // politicId: "", //政治面貌id
        // nationId: "", //民族id
        // posId: "", //职位id
        // jobLevelId: "", //职称id
        // engageForm: "", //聘用形式
        // start: "", //入职日期起始时间
        // end: "", //入职日期结束时间
      },
      //对应显示的内容label为部门名字 选择的值id为部门id
      optionProps: {
        value: "id",
        label: "name",
        children: "children",
        checkStrictly: true, //可以选择任意一级
        expandTrigger: "hover", //鼠标放上触发
      },
      ////新增修改data
      title: "", //新增或修改员工
      depId2: "", //修改部门的回显值
      options: [], //el- cascader选择的数据，获取 到数据后将数据赋值给他
      //新增或者修改员工时必须部门选到最后一级
      optionProps_a: {
        value: "id",
        label: "name",
        children: "children",
        checkStrictly: false, //只能选最后一级菜单
        expandTrigger: "hover", //鼠标放上触发
      },
      //el-cascader 选择之后的id数组 这个要特殊处理 只需用到数组的最后一个值，也就是最终选择的那一项。
      //在发送查询请求时处理 获取该数组的最后一个值：this.depId[this.depId.length - 1] 然后赋值给 search.departmentId
      depId: [],
      joblevels: [], //职称
      positions: [], //职位
      nations: [], //民族             //数据为id和name
      politicsstatus: [], //政治面貌
      departments: [], //部门
      //新增和修改时学历选择
      tiptopDegrees: [
        "本科",
        "大专",
        "硕士",
        "博士",
        "高中",
        "初中",
        "小学",
        "其他",
      ],
      dialogVisible: false, //新增或编辑员工信息
      //新增和修改员工信息绑定的对象
      emp: {
        // workId: "",
      },
      rules: {
        name: [{ required: true, message: "请输入用户名", trigger: "blur" }],
        gender: [{ required: true, message: "请输入性别", trigger: "blur" }],
        birthday: [
          { required: true, message: "请输入出生日期", trigger: "blur" },
        ],
        idCard: [
          { required: true, message: "请输入身份证号码", trigger: "blur" },
          {
            pattern:
              /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
            message: "身份证号码格式不正确",
            trigger: "blur",
          },
        ],
        wedlock: [
          { required: true, message: "请输入婚姻状况", trigger: "blur" },
        ],
        nationId: [{ required: true, message: "请输入您组", trigger: "blur" }],
        nativePlace: [
          { required: true, message: "请输入籍贯", trigger: "blur" },
        ],
        politicId: [
          { required: true, message: "请输入政治面貌", trigger: "blur" },
        ],
        email: [
          { required: true, message: "请输入邮箱地址", trigger: "blur" },
          {
            type: "email",
            message: "邮箱格式不正确",
            trigger: "blur",
          },
        ],
        phone: [{ required: true, message: "请输入电话号码", trigger: "blur" }],
        address: [
          { required: true, message: "请输入员工地址", trigger: "blur" },
        ],
        departmentId: [
          { required: true, message: "请输入部门名称", trigger: "blur" },
        ],
        jobLevelId: [
          { required: true, message: "请输入职称", trigger: "blur" },
        ],
        posId: [{ required: true, message: "请输入职位", trigger: "blur" }],
        engageForm: [
          { required: true, message: "请输入聘用形式", trigger: "blur" },
        ],
        tiptopDegree: [
          { required: true, message: "请输入学历", trigger: "blur" },
        ],
        specialty: [{ required: true, message: "请输入专业", trigger: "blur" }],
        school: [
          { required: true, message: "请输入毕业院校", trigger: "blur" },
        ],
        beginDate: [
          { required: true, message: "请输入入职日期", trigger: "blur" },
        ],
        workState: [
          { required: true, message: "请输入工作状态", trigger: "blur" },
        ],
        workId: [{ required: true, message: "请输入工号", trigger: "blur" }],
        contractTerm: [
          { required: true, message: "请输入合同期限", trigger: "blur" },
        ],
        conversionTime: [
          { required: true, message: "请输入转正日期", trigger: "blur" },
        ],
        notworkDate: [
          { required: true, message: "请输入离职日期", trigger: "blur" },
        ],
        beginContract: [
          { required: true, message: "请输入合同起始日期", trigger: "blur" },
        ],
        endContract: [
          { required: true, message: "请输入合同结束日期", trigger: "blur" },
        ],
        workAge: [{ required: true, message: "请输入工龄", trigger: "blur" }],
      },
      ////导入和导出data
      importDataDisabled: false, //导入数据时不可点击
      importDataBtnText: "导入数据", //导入数据时显示正在导入
    };
  },
  //页面加载 获取所有的用户
  created: function () {
    this.getEmps();
  },
};
</script>


<style lang="less" scoped>



/* 可以设置不同的进入和离开动画 */
/* 设置持续时间和动画函数 */
.slide-fade-enter-active {
  transition: all 0.8s ease;
}

.slide-fade-leave-active {
  transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter, .slide-fade-leave-to
        /* .slide-fade-leave-active for below version 2.1.8 */ {
  transform: translateX(10px);
  opacity: 0;
}
//去掉表格的padding 缩小表格每行的高度
.el-table {
  /deep/ th {
    padding: 10;
  }
  /deep/ td {
    //大于0的时候不管多少都一个效果 ？？？
    padding: 10;
  }
  /deep/ .warning-row {
    //大于0的时候不管多少都一个效果 ？？？
    // padding: 10; background: oldlace;
    background: #e2f7d6;
  }
  /deep/ .success-row {
    //大于0的时候不管多少都一个效果 ？？？
    // padding: 10;
    background: oldlace;
  }
}
</style>