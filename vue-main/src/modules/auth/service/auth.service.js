import axios from 'axios';
import { smallNotification } from '@/services/notification-service';
import store from '@/store';

export default {
  signUpSendStepOneInfo(form, fullForm) {
    console.log(fullForm)
    return axios
      .post('/auth/signup/first', form)
      .then((res) => {
        store.dispatch('updateStepOne', fullForm);
        console.log(res.data)
      })
      .catch((err) => {
        smallNotification('danger', { title: 'Произошла ошибка!' });
        return Promise.reject(err.response.data);
      });
  },
  signUpSendStepTwoInfo(form) {
    return axios
      .post('/auth/signup/second', form)
      .then((res) => {
        store.dispatch('updateStepTwo', form);
      })
      .catch((err) => {
        smallNotification('danger', { title: 'Произошла ошибка!' });
        return Promise.reject(err);
      });
  },
  signUpSendStepThreeInfo(form) {
    return axios
      .post('/auth/signup/third', form)
      .then((res) => {
        store.dispatch('updateStepThree', form);
      })
      .catch((err) => {
        smallNotification('danger', { title: 'Произошла ошибка!' });
        return Promise.reject(err);
      });
  },
};
