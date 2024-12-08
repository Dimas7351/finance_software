import { createStore } from 'vuex';
import { authStore } from '@/modules/auth/store'

const store = createStore({
  modules: {
    authStore
  },
});

export default store;
