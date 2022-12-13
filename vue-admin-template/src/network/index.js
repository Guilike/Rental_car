import store from "@/store";
import { config } from "@vue/test-utils";
import { getCache } from "@/utils/cache";
import Axios from "axios";

const ins = Axios.create({
    baseURL: 'http://localhost:8080'
})

export const get = function get(url, params) {
    return ins.get(url, params);
}

export const post = function post(url, params) {
    return ins.post(url, params);
}


//request拦截器
ins.interceptors.request.use( config => {
    config.headers.token = getCache('token');
    return config;
}, error => {
    console.log(error);
    return Promise.reject(error);
})


ins.interceptors.response.use( response => {
    return response.data;
},error => {
    console.log(error);
})

