<template>
  <div>
    <div style="margin-top: 10px; display: flex; justify-content: center">
      <el-input v-model="keyword" placeholder="通过用户名搜索用户..." prefix-icon="el-icon-search"
        style="width: 400px; margin-right: 10px" @keydown.enter.native="doSearch"></el-input>
      <el-button icon="el-icon-search" type="primary" @click="doSearch">搜索</el-button>
    </div>

    <div class="hr-container">
      <!-- 遍历所有的hr信息 每个hr信息都是一个el-card -->
      <el-card class="hr-card" v-for="(hr, index) in hrs" :key="index">
        <!--名字和删除图标  -->
        <div slot="header" class="clearfix">
          <span>{{ hr.name }}</span>
          <el-button style="float: right; padding: 3px 0; color: #e30007" type="text" icon="el-icon-delete"
            @click="deleteHr(hr)"></el-button>
        </div>
        <!-- 头像 -->
        <div style="display: flex; justify-content: center; margin-bottom: 10px">
          <img src="./images/touxiang.png" alt="" style="width: 60px; height: 60px; border-radius: 50px" />
        </div>
        <!-- 用户信息 -->
        <div class="userinfo-container">
          <div>用户名：{{ hr.name }}</div>
          <div>手机号码：{{ hr.phone }}</div>
          <div>电话号码：{{ hr.telephone }}</div>
          <div>地址：{{ hr.address }}</div>
          <div>
            用户状态：
            <el-switch v-model="hr.enabled" active-text="启用" @change="enabledChange(hr.id, hr.enabled)"
              active-color="#13ce66" inactive-color="#ff4949" inactive-text="禁用" :active-value="1" :inactive-value="0">
            </el-switch>
          </div>
          <div>
            用户角色：
            <el-tag type="success" style="margin-right: 4px" v-for="(role, indexj) in hr.rolesZh" :key="indexj">{{ role
              }}
            </el-tag>
            <!-- 角色列表 -->
            <el-popover placement="right" title="角色列表" @show="showPop(hr)" @hide="hidePop(hr)" width="220"
              trigger="click">
              <el-select v-model="selectedRoles" multiple placeholder="请选择">
                <el-option v-for="(item, i) in allroles" :key="i" :label="item.nameZh" :value="item.nameZh">
                </el-option>
              </el-select>
              <el-button slot="reference" icon="el-icon-more" type="text"></el-button>
            </el-popover>
          </div>
          <div>备注：{{ hr.remark }}</div>
        </div>
      </el-card>
      <!-- 最后一个card 点击添加hr信息 -->
      <!-- style="min-height: 200px" 自己一行时保证card的高度 不会变矮-->
      <el-card class="hr-card" style="min-height: 350px">
        <div style="
            display: flex;
            justify-content: center;
            margin-top: 40%;
            cursor: pointer;
          ">
          <el-tooltip class="item" effect="dark" content="添加新的Hr" placement="right-end">
            <i class="el-icon-circle-plus-outline" @click="addHr"></i>
          </el-tooltip>
        </div>
      </el-card>
    </div>
    <el-dialog title="添加Hr账号" :visible.sync="addHrInfoViewVisible" width="25%">
      <el-form :model="hrForm" label-width="100px" ref="hrForm" :rules="roleInfoRules">
        <el-form-item label="用户昵称" prop="name">
          <el-input :clearable="true" v-model="hrForm.name"> </el-input>
        </el-form-item>
        <el-form-item label="电话号码" prop="phone">
          <el-input :clearable="true" v-model="hrForm.phone"> </el-input>
        </el-form-item>
        <el-form-item label="手机号码" prop="telephone">
          <el-input :clearable="true" v-model="hrForm.telephone"> </el-input>
        </el-form-item>
        <el-form-item label="居住地址" prop="address">
          <el-input :clearable="true" v-model="hrForm.address"> </el-input>
        </el-form-item>
        <el-form-item label="账号" prop="username">
          <el-input :clearable="true" v-model="hrForm.username"> </el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input :clearable="true" v-model="hrForm.password"> </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addHrInfoViewVisible = false">取 消</el-button>
        <el-button type="primary" @click="addHrInfo('hrForm')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Hrinfo from "./Hrinfo";
export default {
  comments: {
    Hrinfo,
  },
  data() {
    //昵称校验方法
    var validateName = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入昵称"));
      } else {
        callback();
      }
    };
    return {
      hrs: {}, //所有的hr信息
      allroles: [], //所有角色信息
      selectedRoles: [], //当前选择的角色
      keyword: "",
      addHrInfoViewVisible: false,
      hrForm: {
        name: "",
        phone: "",
        telephone: "",
        address: "",
        username: "",
        password: "",
      },
      //修改用户信息时 昵称的校验规则
      roleInfoRules: {
        name: [{ validator: validateName, trigger: "blur" }],
      },
    };
  },
  methods: {
    addHr() {
      this.addHrInfoViewVisible = true;
    },
    addHrInfo(roleInfo) {
      this.$refs[roleInfo].validate((valid) => {
        if (valid) {
          this.postRequest("/addHrInfo", this.hrForm).then((resp) => {
            if (resp) {
              this.getHrs();
              this.addHrInfoViewVisible = false;
            }
          });
        } else {
          return false;
        }
      });
    },
    deleteHr(hr) {
      this.$confirm("此操作将删除该Hr的信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.getRequest("/deleteHr", {
          hrId: hr.id,
        }).then((resp) => {
          if (resp) {
            this.getHrs();
          }
        });
      });
    },
    doSearch() {
      this.getHrs();
    },
    getHrs() {
      this.getRequest("/getHrs", {
        keyword: this.keyword,
      }).then((resp) => {
        if (resp) {
          this.hrs = resp.data;
        }
      });
    },
    showPop(hr) {
      this.initAllRoles();
      let roles = hr.rolesZh;
      this.selectedRoles = [];
      roles.forEach((r) => {
        this.selectedRoles.push(r);
      });
    },
    initAllRoles() {
      this.getRequest("/getRoles").then((resp) => {
        if (resp) {
          this.allroles = resp.data;
        }
      });
    },
    //修改hr的使能情况
    enabledChange(id, enabled) {
      this.getRequest("/modifyEnabled", { id: id, enabled: enabled }).then(
        (resp) => {
          if (resp) {
            this.getHrs();
          }
        }
      );
    },
    hidePop(hr) {
      //新增前要判断 当前选中的角色中，去掉目前hr有的角色
      //遍历选中的角色，如果是目前hr有的角色 就从selectedRoles中删掉
      // for (var i = this.selectedRoles.length - 1; i >= 0; i--) {
      //     for (var j = hr.rolesZh.length - 1; j >= 0; j--) {
      //         if (this.selectedRoles[i] == hr.rolesZh[j]) {
      //             this.selectedRoles.splice(i, 1);
      //             break;
      //         }
      //     }
      // }
      //发送请求 给hr新增角色
      this.postRequest("/modifyRoles", {
        id: hr.id,
        rolesZh: this.selectedRoles,
      }).then((resp) => {
        if (resp) {
          this.getHrs();
        }
      });
      // console.log(this.selectedRoles)
    },
    del(item, arr) {
      for (let i = 0; i < arr.length; i++) {
        if (arr[i] == item) {
          arr.splice(i, 1);
        }
      }
      // console.log(this.listVar)
    },
  },
  created: function () {
    this.getHrs();
  },
};
</script>

<style>

.userinfo-container div {
  font-size: 12px;
  color: #409eff;
}
.hr-container {
  margin-left: 20px;
  margin-top: 10px;
  display: flex;
  /* 必要的时候可以拆行 */
  flex-wrap: wrap;
  /* 盒子平均分配居中布局 但是最后一行盒子不满的话 也会居中 */
  /* justify-content: space-around; */
}

/* justify-content: space-around; 让最后一行元素靠左对齐 效果是最后一行和上一行不对齐 所以没用  */

/* 
// .hr-container::after {
//   content: "";
//   display: block;
//   flex: 1; /* 与flex: auto;等效都是自适应剩余空间
}
*/
/* 采用固定盒子 也就是card加左右边距margin */
.hr-card {
  width: 350px;
  margin: 20px;
}
</style>