<script>
import ContainerCard from '@/components/ContainerCard.vue';
import { defineComponent, ref } from 'vue';
import { smallNotification } from '@/services/notification-service';
import AuthService from '@/modules/auth/service/auth.service';
import router from '@/router';

export default defineComponent({
  name: 'PageLogin',
  components: { ContainerCard },
  setup() {
    const form = ref({
      login: '',
      password: '',
    });

    const onNext = () => {
      // if (form.value.login && form.value.password) {
      //   AuthService.login({
      //     email: form.value.login,
      //     password: form.value.password,
      //   }).then(() => {
          router.push({ name: 'PageBudget' });
      //   });
      // } else {
      //   smallNotification('danger', { title: 'Заполните все поля!' });
      // }
    };

    return {
      form,
      onNext,
    };
  },
});
</script>

<template>
  <div class="container">
    <ContainerCard header-text="Вход" style="width: 800px; height: 465px">
      <template #card-body>
        <div class="--flex-row" style="width: 750px; gap: 62px">
          <div class="login-inputs-wrapper">
            <div class="form-group">
              <label for="login">Логин</label>
              <input id="login" type="text" class="form-input" v-model="form.login" />
            </div>
            <div class="form-group --mb-0">
              <label for="password">Логин</label>
              <input
                id="password"
                type="password"
                class="form-input --mb-2"
                v-model="form.password"
              />
              <router-link
                class="--text-muted"
                style="font-size: 14px"
                :to="{ name: 'PagePasswordRecovery' }"
              >
                <span class="--text-decoration-underline">Забыли пароль?</span>
              </router-link>
            </div>
          </div>
          <div class="login-internal-wrapper">
            <a href="#" class="login-internal-group">
              <div class="login-internal-group__icon --google-icon"></div>
              <div class="login-internal-group__text">Продолжить с Google</div>
            </a>
            <a href="#" class="login-internal-group">
              <div class="login-internal-group__icon --yandex-icon"></div>
              <div class="login-internal-group__text">Продолжить с Yandex</div>
            </a>
            <a href="#" class="login-internal-group">
              <div class="login-internal-group__icon --vk-icon"></div>
              <div class="login-internal-group__text">Продолжить с VK</div>
            </a>
          </div>
        </div>
      </template>
      <template #card-footer>
        <div @click.prevent="onNext">
          <div class="button button-sm">Войти</div>
        </div>
      </template>
    </ContainerCard>
  </div>
</template>
