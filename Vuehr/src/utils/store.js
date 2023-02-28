import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

//创建store对象
const store = new Vuex.Store({
    // state 中存放的就是全局共享的数据
    state: {
        roles: JSON.parse(sessionStorage.getItem("COMPANY_AUTH_INFO")) || {},
        hrId: JSON.parse(sessionStorage.getItem("HR_ID")) || {}
    },
    // 变更数据的方法，可以调用此方法修改state中数据
    mutations: {
        add(state, step) {
            state.roles = step //将step的值赋给state.roles
            sessionStorage.setItem("COMPANY_AUTH_INFO", JSON.stringify(step))
            // sessionStorage.setItem("HR_ID", JSON.stringify(step.id))
        },
        addid(state, step) {
            state.hrId = step //将step的值赋给state.roles
            sessionStorage.setItem("HR_ID", JSON.stringify(step))
        }
    },
    //异步操作变更数据
    actions: {
        addAsync(context, step) {
            //context：上下文对象，相当于箭头函数中的this，和 store 实例具有相同的属性和方法
            context.commit('add', step)

        },
        addasyid(context, step) {
            context.commit('addid', step)
        }
    },
    getters: {
        hasPermission: state => (id) => {
            //该find方法是 判断permissionList权限数组中的值是否有某个功能权限值 
            //有的话就返回权限数组 数组!=undefined 为true 
            //没有的话回返回undefinde undefinde!=undefind 为false
            return state.roles.find((item) => item == id) != undefined
        },

    },
})
// 全局可用
export default store;