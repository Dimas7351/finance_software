export const authStore = {
  state: {
    signUp: {
      stepOne: {},
      stepTwo: {},
      stepThree: {},
    },
  },
  mutations: {
    setStepOne(state, data) {
      state.signUp.stepOne = data;
    },
    setStepTwo(state, data) {
      state.signUp.stepTwo = data;
    },
    setStepThree(state, data) {
      state.signUp.stepThree = data;
    },
  },
  actions: {
    updateStepOne({ commit }, data) {
      commit('setStepOne', data);
    },
    updateStepTwo({ commit }, data) {
      commit('setStepTwo', data);
    },
    updateStepThree({ commit }, data) {
      commit('setStepThree', data);
    },
  },
  getters: {
    getStepOne(state) {
      return state.signUp.stepOne;
    },
    getStepTwo(state) {
      return state.signUp.stepTwo;
    },
    getStepThree(state) {
      return state.signUp.stepThree;
    },
  },
};
