import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: ''
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  }
}

const actions = {
  // 登录
  login({ commit }, userInfo) {
    const data = { 'token': 'admin' }
    setToken(data.token)
    commit('SET_TOKEN', data.token)

  // const username = userInfo.username.trim()
  // return new Promise((resolve, reject) => {
  //   login(username, userInfo.password).then(response => {
  //     const data = response.data
  //     setToken(data.token)
  //     commit('SET_TOKEN', data.token)
  //     resolve()
  //   }).catch(error => {
  //     reject(error)
  //   })
  // })
  },

  // 获取用户信息
  getInfo({ commit, state }) {
    const data = { 'roles': 'admin', 'name': 'admin', 'avatar': 'https://chen01-edu.oss-cn-beijing.aliyuncs.com/2022/12/21/b96d65ffcebd4a99b31d114973a7eb38file.png' }
    if (data.roles && data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
      commit('SET_ROLES', data.roles)
    } else {
      reject('getInfo: roles must be a non-null array !')
    }
    commit('SET_NAME', data.name)
    commit('SET_AVATAR', data.avatar)

  // return new Promise((resolve, reject) => {
  //   getInfo(state.token).then(response => {
  //     const data = response.data
  //     if (data.roles && data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
  //       commit('SET_ROLES', data.roles)
  //     } else {
  //       reject('getInfo: roles must be a non-null array !')
  //     }
  //     commit('SET_NAME', data.name)
  //     commit('SET_AVATAR', data.avatar)
  //     resolve(response)
  //   }).catch(error => {
  //     reject(error)
  //   })
  // })
  },

  // 登出
  logout({ commit, state }) {
    commit('SET_TOKEN', '')
    commit('SET_ROLES', [])
    removeToken()
  // return new Promise((resolve, reject) => {
  //   logout(state.token).then(() => {
  //     commit('SET_TOKEN', '')
  //     commit('SET_ROLES', [])
  //     removeToken()
  //     resolve()
  //   }).catch(error => {
  //     reject(error)
  //   })
  // })
  },

  // 前端 登出
  FedLogOut({ commit }) {
    commit('SET_TOKEN', '')
    removeToken()
  // return new Promise(resolve => {
  //   commit('SET_TOKEN', '')
  //   removeToken()
  //   resolve()
  // })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

