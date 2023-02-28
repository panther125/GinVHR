<template>
  <div>
    <el-container>
      <!-- 顶部 -->
      <el-header class="homeHeader">
        <div class="totalheader">
          <!-- 标题 -->
          <div class="title">微人事</div>
          <div class="box2">
            <!-- <img src="./images/logo.jpg" style="height:60px;width: 300px;"  alt=""> -->
          </div>
          <!-- 右侧账户设置 -->
          <div>
            <!-- 下拉菜单 -->
            <el-dropdown @command="commandHandler">
              <span class="el-dropdown-link">
                {{ role }}<img src="./images/touxiang.png" alt="" />
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
                <el-dropdown-item
                  v-if="hasPermission('ROLE_admin')"
                  command="setting"
                  >设置
                </el-dropdown-item>
                <el-dropdown-item command="logout" divided
                  >注销登录</el-dropdown-item
                >
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
      </el-header>
      <el-container>
        <!-- 左侧导航栏 -->
        <el-aside width="200px">
          <!-- :unique-opened="true" 只有一个子菜单打开 -->
          <el-menu
            default-active="1"
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose"
            active-text-color="#409eff"
            :unique-opened="true"
          >
            <el-submenu index="1">
              <template slot="title">
                <i class="el-icon-user-solid" style="color: #409eff"></i>
                <span>员工资料</span>
              </template>
              <!-- 点击进行路由切换 -->
              <el-menu-item index="1-1" @click="handleRoute('/empinfo')">
                <i class="el-icon-menu"></i>
                <span slot="title">基本资料</span>
              </el-menu-item>
            </el-submenu>

            <el-submenu index="2">
              <template slot="title">
                <i class="el-icon-location" style="color: #409eff"></i>
                <span>人事管理</span>
              </template>
              <el-menu-item index="2-1" @click="handleRoute('/empaward')"
                >员工奖惩</el-menu-item
              >
              <el-menu-item index="2-2" @click="handleRoute('/emptrain')"
                >员工培训</el-menu-item
              >
              <el-menu-item index="2-3" @click="handleRoute('/empmove')"
                >员工调动</el-menu-item
              >

              <el-submenu index="2-4">
                <template slot="title">员工调薪</template>
                <el-menu-item
                  index="2-4-1"
                  @click="handleRoute('/empmodsalary/empsalaryraise')"
                  >员工加薪</el-menu-item
                >
                <el-menu-item
                  index="2-4-2"
                  @click="handleRoute('/empmodsalary/empsalarycut')"
                  >员工降薪</el-menu-item
                >
              </el-submenu>
            </el-submenu>

            <el-menu-item index="3">
              <i class="el-icon-document" style="color: #409eff"></i>
              <span slot="title">导航三</span>
            </el-menu-item>
            <el-menu-item index="4">
              <i class="el-icon-setting" style="color: #409eff"></i>
              <span slot="title">导航四</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-container>
          <!-- 主体内容 -->
          <el-main>
            <!-- 面包屑导航 不是主页的话进行显示 -->
            <el-breadcrumb
              separator-class="el-icon-arrow-right"
              v-if="this.$router.currentRoute.path != '/home'"
            >
              <!-- 显示首页 点击可回到首页 -->
              <el-breadcrumb-item :to="{ path: '/home' }"
                >首页</el-breadcrumb-item
              >
              <!-- 显示首页之后的路径 -->
              <!-- <el-breadcrumb-item>
                {{ this.$router.currentRoute.name }}
              </el-breadcrumb-item> -->

              <!-- 遍历显示除首页的其他路径 -->
              <el-breadcrumb-item
                v-for="(item, index) in $route.matched.slice(1)"
                :key="index"
              >
                {{ item.name }}</el-breadcrumb-item
              >
            </el-breadcrumb>

            <router-view> </router-view>

            <div
              class="homeWelcome"
              v-if="this.$router.currentRoute.path == '/home'"
            >
              欢迎来到微人事！
            </div>
          </el-main>
          <!-- 底部 -->
          <el-footer>
            <el-link type="info" href="http://www.gincode.icu" target="_blank"
              >友情链接</el-link
            >
          </el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>


<script>
export default {
  data() {
    return {
      role: this.hasPermission("ROLE_admin") ? "超级管理员" : "管理员",
    };
  },
  methods: {
    // 查询是否有权限
    hasPermission(id) {
      return this.$store.getters.hasPermission(id); //这是通过getters来获取 在getters中做了些操作
    },

    //点击菜单项的处理方法
    handleRoute(route) {
      // alert(this.$store.state.count.indexOf("admin"));
      //必须大写O
      // alert(this.roles.indexof("admin"));
      // alert(this.roles.indexOf("admin"));
      //判断角色 加薪降薪只有超级管理员才能操作
      if (route.indexOf("empmodsalary") != -1) {
        // this.$store.state.roles.indexOf("ROLE_admin") == -1
        if (!this.hasPermission("ROLE_admin")) {
          this.$message.error({ message: "权限不足，请联系管理员" });
          return;
        }
      }

      this.$router.push(route);
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    commandHandler(cmd) {
      if (cmd == "logout") {
        this.$confirm("此操作将注销登录, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.getRequest("/logout");
            // window.sessionStorage.removeItem("user");
            // this.$store.commit("initRoutes", []);
            this.$router.replace("/");
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消操作",
            });
          });
      } else if (cmd == "userinfo") {
        this.$router.push("/hrinfo");
      } else if (cmd == "setting") {
        this.$router.push("/setting");
      }
    },
    // uriList() {
    //   let uriList = this.$route.matched;
    //   uriList.shift();
    //   return uriList;
    // },
  },
  // computed: {
  //   uriList() {
  //     let uriList = this.$route.matched;
  //     uriList.shift();
  //     return uriList;
  //   },
  // },
};
</script>


<style scoped >
.totalheader {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* 顶部右侧样式 */
.el-dropdown-link img {
  /* 指定图片尺寸 并且变成圆形 */
  width: 48px;
  height: 48px;
  border-radius: 24px;
  margin-left: 8px;
  /* margin-top: 26px; */
}

.el-dropdown-link {
  height: 60px;
  /* 将盒子变成弹性布局  */
  display: flex;
  /* 盒子中的元素 按照主轴居中对齐 默认主轴为横向 */
  align-items: center;
  font-size: medium;
  color: #ebeff3ee;
  font-family: 楷体;
}
/* .el-icon-arrow-down {
  margin-right: 50px;
  font-size: 12px;
} */

/* 顶部右侧样式 保证两个div在同一行 */
.el-dropdown {
  float: right;
}

/* 顶部左侧样式 */
/* 两个div同行显示 float属性 */
.title {
  text-align: left;
  font-size: 30px;
  font-family: 楷体;
  color: #ebeff3ee;
  float: left;
}

/* 顶部样式 */
.homeWelcome {
  text-align: center;
  font-size: 30px;
  font-family: 楷体;
  color: #409eff;
  margin-top: calc(50vh - 60px);
}

.el-header {
  background-color: #409eff;
  line-height: 60px;
}

/* 底部样式 */
.el-footer {
  /* background-color: #c8d3d670; */
  color: #333;
  text-align: center;
  line-height: 55px;
}

/* 左侧导航栏样式 */
.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
  /*container占满全屏 解决屏幕超出一页 把多出的底部的高度减去 */
  height: calc(100vh - 60px);
}

.el-main {
  /* background-color: #c8d3d670; */
  color: #333;
  /* text-align: center; */
  /* line-height: 160px; */
  /* 背景图片 */
  /* background-image:url("./images/gradient2.png"); */
  /* background-repeat: repeat-x; */
  /* background-size: 100% 100%; */
  /* background-attachment: fixed; */
  /* 加上这个满屏的时候 底部仍然不动 */
  height: 300px;
}

/* el引入的样式 容器布局 */
body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}

/* 左侧菜单也占满屏幕 */
.el-menu {
  height: 100%;
  text-align: left;
}
</style>