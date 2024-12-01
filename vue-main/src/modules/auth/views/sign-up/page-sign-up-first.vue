<script>
import ContainerCard from '@/components/ContainerCard.vue';
import ProgressBar from '@/components/ProgressBar.vue';
import Tooltip from '@/components/Tooltip.vue';
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'PageSignUpFirst',
  components: { ContainerCard, ProgressBar, Tooltip },
  setup() {
    const form = ref({
      name: '',
      email: '',
      login: '',
      password: '',
      passwordConfirm: '',
    });

    return {
      form,
    };
  },
});
</script>

<template>
  <div class="container">
    <ContainerCard header-text="Регистрация">
      <template #card-header>
        <ProgressBar :steps-count="3" />
      </template>
      <template #card-body>
        <div style="width: 350px">
          <div class="form-group">
            <label for="name"> Имя </label>
            <div class="--flex-row --w-100">
              <input id="name" class="form-input" v-model="form.name" />
              <Tooltip :tooltip-font-size="'14px'" width="300px" position="right">
                <template #element>
                  <span class="question-icon --ml-3">?</span>
                </template>
                <template #content> Будем знать, как к вам обращаться </template>
              </Tooltip>
            </div>
          </div>
          <div class="form-group">
            <label for="email">Адрес электронной почты</label>
            <input id="email" class="form-input" v-model="form.email" />
          </div>
          <div class="form-group">
            <label for="login">Логин</label>
            <input id="login" class="form-input" v-model="form.login" />
          </div>
          <div class="form-group">
            <label for="password">Пароль</label>
            <input id="password" class="form-input" v-model="form.password" />
          </div>
          <div class="form-group">
            <label for="passwordConfirm">Повторите пароль</label>
            <input id="passwordConfirm" class="form-input" v-model="form.passwordConfirm" />
          </div>
        </div>
      </template>

      <template #card-footer> Footer </template>
    </ContainerCard>
  </div>
</template>
