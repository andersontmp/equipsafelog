import Vuex from 'vuex';

export default new Vuex.Store({
  state: {
    token: null,
    user: null,
    pass: null,
    role: null
  },
  mutations: {
    setData(state, payload) {
      state.dados = payload;
    }
  },
  actions: {
    saveData({ commit }, dados) {
      commit('setData', dados);
    }
  },
  getters: {
    getData(state) {
      return state.dados;
    }
  }
});
