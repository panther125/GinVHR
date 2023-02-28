import axios from 'axios'
import { Message } from 'element-ui';
import router from '../router'


axios.interceptors.response.use(success => {
    // console.log(success.data);
    // 判断后端传回的状态码
    if (success.data.code >= 80001) {
        // Message.error(success.data.Message)
        // console.log(123);
        // 显示错误消息
        Message.error({ message: success.data.msg })
        //90001为会话过期的状态码，90006为登录失效的状态码 跳转到首页
        if (success.data.code == 90001 || success.data.code == 90006 || success.data.code == 90008)
            router.replace("/")
        return;
    }
    //显示其他消息 如登陆成功之类的
    if (success.data.msg) {
        Message.success({ message: success.data.msg })
    }
    return success.data;
}, error => {
    // console.log(error);
    //错误消息提示
    if (error.response.status == 504 || error.response.status == 404) {
        Message.error({ message: '服务器被吃了( ╯□╰ )' })
    } else if (error.response.status == 403) {
        Message.error({ message: '权限不足，请联系管理员' })
    } else if (error.response.status == 401) {
        // mymessage.error({ message: error.response.data.msg ? error.response.data.msg : '尚未登录，请登录' })
        router.replace('/');
    } else {
        if (error.response.data.msg) {
            Message.error({ message: error.response.data.msg })
        } else {
            Message.error({ message: '未知错误!' })
        }
    }
    return;
})

let base = '/vhr';

export const postKeyValueRequest = (url, params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params,
        transformRequest: [function (data) {
            let ret = '';
            for (let i in data) {
                ret += encodeURIComponent(i) + '=' + encodeURIComponent(data[i]) + '&'
            }
            return ret;
        }],
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    });
}
export const postRequest = (url, params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params,
    })
}
export const putRequest = (url, params) => {
    return axios({
        method: 'put',
        url: `${base}${url}`,
        data: params
    })
}
export const getRequest = (url, params) => {
    return axios({
        method: 'get',
        url: `${base}${url}`,
        params: params,

    })
}
export const deleteRequest = (url, params) => {
    return axios({
        method: 'delete',
        url: `${base}${url}`,
        params: params
    })
}
