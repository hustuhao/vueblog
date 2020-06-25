import axios from 'axios'
import Element from 'element-ui'
import router from './router'
import store from './store'


//服务端地址
axios.defaults.baseURL = "http://localhost:8081" 
// 在main.js 中或者 axios.js 中增加这句代码，否则请求将不带有 cookie。
// axios.defaults.withCredentials = true;

// 前置拦截
axios.interceptors.request.use(config => {
  return config
})

axios.interceptors.response.use(response => {
    let res = response.data;

    console.log("=================")
    console.log(res)
    console.log("=================")

    if (res.code === 200) {
      return response
    } else {

      Element.Message.error(res.msg, {duration: 3 * 1000})

      return Promise.reject(response.data.msg)
    }
  },
  error => {
    console.log(error)
    if(error.response.data) {
      error.message = error.response.data.msg
    }

    if(error.response.status === 401) {
      store.commit("REMOVE_INFO")
      router.push("/login")
    }

    Element.Message.error(error.message, {duration: 3 * 1000})
    return Promise.reject(error)
  }
)