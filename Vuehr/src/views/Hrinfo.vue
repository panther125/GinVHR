<template>
  <div>
    <el-card class="box-card">
      <!--昵称 -->
      <div slot="header" class="clearfix">
        <span>{{ roleInfo.name }}</span>
      </div>
      <div>
        <!-- 头像 这里采用本地存储  -->
        <div
          style="display: flex; justify-content: center; margin-bottom: 10px"
        >
          <img
            src="./images/touxiang.png"
            alt=""
            style="width: 100px; height: 100px; border-radius: 50px"
          />
        </div>
        <!-- 用户信息 -->
        <!-- <el-tag> 标签 -->
        <div>
          电话号码：
          <el-tag>{{ roleInfo.telephone }}</el-tag>
        </div>
        <div style="margin-top: 5px; margin-bottom: 5px">
          手机号码：
          <el-tag>{{ roleInfo.phone }}</el-tag>
        </div>
        <div>
          居住地址：
          <el-tag>{{ roleInfo.address }}</el-tag>
        </div>
        <!-- 当前hr拥有的角色 -->
        <div style="margin-top: 5px">
          用户标签：
          <el-tag
            type="success"
            style="margin-right: 5px"
            v-for="(r, index) in roleInfo.roles"
            :key="index"
          >
            {{ r.nameZh }}
          </el-tag>
        </div>
        <!-- 两个按钮 -->
        <div
          style="display: flex; justify-content: space-around; margin-top: 10px"
        >
          <el-button type="primary" @click="updateHrInfo">修改信息</el-button>
          <el-button type="danger" @click="passwordFormVisible = true"
            >修改密码</el-button
          >
        </div>
      </div>
    </el-card>

    <el-dialog
      title="个人信息修改"
      :visible.sync="updateHrInfoViewVisible"
      width="25%"
    >
      <el-form
        :model="hrForm"
        label-width="100px"
        ref="hrForm"
        :rules="roleInfoRules"
      >
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updateHrInfoViewVisible = false">取 消</el-button>
        <el-button type="primary" @click="modifyUserInfo('hrForm')"
          >确 定</el-button
        >
      </div>
    </el-dialog>

    <el-dialog title="密码修改" :visible.sync="passwordFormVisible" width="25%">
      <el-form
        :model="ruleForm"
        :rules="rules"
        label-width="100px"
        ref="ruleForm"
      >
        <el-form-item label="请输入旧密码" prop="oldpass">
          <el-input
            type="password"
            show-password
            :clearable="true"
            v-model="ruleForm.oldpass"
            autocomplete="off"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="请输入新密码" prop="pass">
          <el-input
            type="password"
            show-password
            :clearable="true"
            v-model="ruleForm.pass"
            autocomplete="off"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="checkPass">
          <el-input
            type="password"
            show-password
            :clearable="true"
            v-model="ruleForm.checkPass"
            autocomplete="off"
          >
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="passwordFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="modefyPassword('ruleForm')"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    //第一个密码校验方法
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    //昵称校验方法
    var validateName = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入昵称"));
      } else {
        callback();
      }
    };
    //第二个密码校验方法
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      updateHrInfoViewVisible: false,
      passwordFormVisible: false,
      roleInfo: {},
      hrId: this.$store.state.hrId,

      //修改密码时的表单数据
      ruleForm: {
        oldpass: "",
        pass: "",
        checkPass: "",
      },
      //修改用户信息的临时表单数据
      hrForm: {
        name: "",
        phone: "",
        telephone: "",
        address: "",
      },
      //修改密码的校验规则
      rules: {
        oldpass: [{ validator: validatePass, trigger: "blur" }],
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
      },
      //修改用户信息时 昵称的校验规则
      roleInfoRules: {
        name: [{ validator: validateName, trigger: "blur" }],
      },
    };
  },
  methods: {
    //获取用户信息
    getHrinfo() {
      // alert(this.hrId);
      this.getRequest("/hr/info", {
        hrId: this.hrId,
      }).then((resp) => {
        if (resp) {
          this.roleInfo = resp;
        }
      });
    },
    //发请求修改密码
    modefyPassword(formName) {
      //el form 中需要加ref="ruleForm" 不然会报错： undefined (reading 'validate')
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.ruleForm.hrId = this.hrId;
          this.putRequest("/hr/pass", this.ruleForm).then((resp) => {
            if (resp) {
              this.getRequest("/logout");
              this.$router.replace("/");
            }
          });
        } else {
          return false;
        }
      });
    },
    //发送请求修改用户信息
    modifyUserInfo(roleInfo) {
      this.$refs[roleInfo].validate((valid) => {
        if (valid) {
          this.putRequest("/hr/info", this.hrForm).then((resp) => {
            if (resp) {
              this.getHrinfo();
              this.updateHrInfoViewVisible = false;
            }
          });
        } else {
          return false;
        }
      });
    },
    //点修改信息的事件 弹出修改框 给hrForm赋值 赋值用深拷贝的方式
    updateHrInfo() {
      this.updateHrInfoViewVisible = true;
      //深拷贝 浅拷贝的话改这个值 原数值也会改
      this.hrForm = JSON.parse(JSON.stringify(this.roleInfo));
    },
  },
  created: function () {
    this.getHrinfo();
  },
};
</script>

<style>
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  margin-top: 10px;
  width: 400px;
}
</style>