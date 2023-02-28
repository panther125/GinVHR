<template>
  <div>
    <el-form
      :rules="rules"
      ref="loginForm"
      v-loading="loading"
      element-loading-text="正在登录..."
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0, 0, 0, 0.8)"
      :model="loginForm"
      class="loginContainer"
    >
      <h3 class="loginTitle">系统登录</h3>
      <el-form-item prop="username">
        <el-input
          size="normal"
          type="text"
          v-model="loginForm.username"
          auto-complete="off"
          placeholder="请输入用户名"
          @blur="checkUsername()"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          size="normal"
          type="password"
          v-model="loginForm.password"
          auto-complete="off"
          placeholder="请输入密码"
          :show-password="true"
        >
        </el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input
          size="normal"
          type="text"
          v-model="loginForm.code"
          auto-complete="off"
          placeholder="点击图片更换验证码"
          @keydown.enter.native="submitLogin"
          style="width: 250px"
        ></el-input>
        <img
          :src="vcUrl"
          @click="updateVerifyCode"
          alt=""
          style="cursor: pointer"
        />
      </el-form-item>
      <el-button
        size="normal"
        type="primary"
        style="width: 100%"
        @click="submitLogin"
        >登录</el-button
      >
    </el-form>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      loading: false,
      // 验证码 加载时从后台获取
      vcUrl: "/vhr/verifyCode?time=" + new Date(),
      //表单内容 指定默认的账号密码
      loginForm: {
        username: "admin",
        password: "12345",
        code: "",
      },
      // 校验规则
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        code: [{ required: true, message: "请输入验证码", trigger: "blur" }],
      },
    };
  },
  methods: {
    // 检查用户名是否存在 失去焦点校验
    checkUsername() {
      this.getRequest("/checkUsername", {
        username: this.loginForm.username,
      });
    },
    //点击更新验证码
    updateVerifyCode() {
      this.loginForm.code = "";
      this.vcUrl = "/vhr/verifyCode?time=" + new Date();
    },
    // 提交表单
    submitLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          this.postRequest("/doLogin", this.loginForm).then((resp) => {
            this.loading = false;
            if (resp) {
              //全局参数 该账户拥有的角色
              this.$store.dispatch("addAsync", resp.data.roles);
              this.$store.dispatch("addasyid", resp.data.id);
              // this.$store.commit('INIT_CURRENTHR', resp.obj);
              // window.sessionStorage.setItem("user", JSON.stringify(resp.obj));
              let path = this.$route.query.redirect;
              //页面跳转
              this.$router.replace(
                path == "/" || path == undefined ? "/home" : path
              );
            } else {
              // 清空验证码输入框
              this.loginForm.code = "";
              // 重新获取验证码
              this.vcUrl = "/vhr/verifyCode?time=" + new Date();
            }
          });
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style>
body{
  background-image: url(https://www.zpzpup.com/assets/image/bj02.jpg);
}
.loginContainer {
  /* 边框圆角半径 */
  border-radius: 15px;
  background-clip: padding-box;
  /* 外边距 */
  margin: 180px auto;
  width: 350px;
  padding: 15px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.loginTitle {
  margin: 15px auto 20px auto;
  text-align: center;
  color: #505458;
}

.el-form-item__content {
  display: flex;
  align-items: center;
}
</style>
