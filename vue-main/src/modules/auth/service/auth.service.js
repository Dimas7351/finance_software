import axios from 'axios';
import { smallNotification } from '@/services/notification-service';
import store from '@/store';

export default {
  signUpSendStepOneInfo(form) {
    return axios
      .post('/signup/first', form)
      .then((res) => {
        store.dispatch('setStepOne', form);
      })
      .catch((err) => {
        smallNotification('danger', { title: 'Произошла ошибка!' });
        return Promise.reject(err);
      });
  },
  signUpSendStepTwoInfo(form) {
    return axios
      .post('/signup/second', form)
      .then((res) => {
        store.dispatch('setStepTwo', form);
      })
      .catch((err) => {
        smallNotification('danger', { title: 'Произошла ошибка!' });
        return Promise.reject(err);
      });
  },
  signUpSendStepThreeInfo(form) {
    return axios
      .post('/signup/third', form)
      .then((res) => {
        store.dispatch('setStepThree', form);
      })
      .catch((err) => {
        smallNotification('danger', { title: 'Произошла ошибка!' });
        return Promise.reject(err);
      });
  },
};
