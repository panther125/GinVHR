import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/Login.vue'
import Home from './views/Home.vue'
import EmpInfo from './views/emp/EmpInfo.vue'
import EmpAward from './views/emp/EmpAward.vue'
import HrInfo from './views/Hrinfo.vue'
import EmpModSalary from './views/emp/EmpModSalary.vue'
import EmpMove from './views/emp/EmpMove.vue'
import EmpTrain from './views/emp/EmpTrain.vue'
import EmpSalaryCut from './views/emp/salary/EmpSalaryCut.vue'
import EmpSalaryRaise from './views/emp/salary/EmpSalaryRaise.vue'
import Error from './views/Error.vue'
import Setting from './views/Setting.vue'
Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'Login',
            component: Login,
            hidden: true
        },
        {
            path: '/home',
            name: '首页',
            component: Home,
            hidden: true,
            children: [
                // 员工资料部分
                {
                    path: '/empinfo',
                    name: '员工基本资料',
                    component: EmpInfo,
                    hidden: true
                },
                //人事管理部分
                {
                    path: '/empaward',
                    name: '员工奖惩信息',
                    component: EmpAward,
                    hidden: true
                },
                {
                    path: '/emptrain',
                    name: '员工培训',
                    component: EmpTrain,
                    hidden: true
                },
                {
                    path: '/empmodsalary',
                    name: '员工调薪',
                    component: EmpModSalary,
                    // hidden: true,
                    children: [
                        {
                            path: 'empsalarycut',
                            name: '员工降薪',
                            component: EmpSalaryCut,
                            // hidden: true
                        },
                        {
                            path: 'empsalaryraise',
                            name: '员工加薪',
                            component: EmpSalaryRaise,
                            // hidden: true
                        },
                    ]
                },
                {
                    path: '/empmove',
                    name: '员工调动',
                    component: EmpMove,
                    hidden: true
                },


                //头像个人信息处理
                {
                    path: '/hrinfo',
                    name: '个人中心',
                    component: HrInfo,
                    hidden: true
                },
                //头像个人信息处理
                {
                    path: '/setting',
                    name: '设置',
                    component: Setting,
                    hidden: true
                },
            ]
        },
        {
            path: '/error',
            name: 'Error',
            component: Error,
            hidden: true
        }
    ]
})