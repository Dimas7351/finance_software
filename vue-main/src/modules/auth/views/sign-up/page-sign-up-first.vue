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
  <div style="width: 344px">
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
      <div class="--flex-row --w-100">
        <input id="email" class="form-input" v-model="form.email" />
        <Tooltip :tooltip-font-size="'14px'" width="300px" position="right">
          <template #element>
            <span class="question-icon --ml-3">?</span>
          </template>
          <template #content> Используем его для восстановления доступа к аккаунту </template>
        </Tooltip>
      </div>
    </div>
    <div class="form-group">
      <label for="login">Логин</label>
      <div class="--flex-row --w-100">
        <input id="login" class="form-input" v-model="form.login" />
        <Tooltip :tooltip-font-size="'14px'" width="350px" position="right">
          <template #element>
            <span class="question-icon --ml-3">?</span>
          </template>
          <template #content>
            Будет использоваться для вашей идентификации в системе. Цифры и латиница, >6 символов
          </template>
        </Tooltip>
      </div>
    </div>
    <div class="form-group">
      <label for="password">Пароль</label>
      <div class="--flex-row --w-100">
        <input type="password" id="password" class="form-input" v-model="form.password" />
        <Tooltip :tooltip-font-size="'14px'" width="350px" position="right">
          <template #element>
            <span class="question-icon --ml-3">?</span>
          </template>
          <template #content>
            Будет использоваться для входа в аккаунт. Цифры, латиница и знаки препинания, >10
            символов
          </template>
        </Tooltip>
      </div>
    </div>
    <div class="form-group">
      <label for="passwordConfirm">Повторите пароль</label>
      <div class="--flex-row --w-100">
        <input
          type="password"
          id="passwordConfirm"
          class="form-input"
          v-model="form.passwordConfirm"
        />
        <Tooltip :tooltip-font-size="'14px'" width="350px" position="right">
          <template #element>
            <span class="question-icon --ml-3">?</span>
          </template>
          <template #content> Введите пароль еще раз, чтобы избежать ошибки при вводе </template>
        </Tooltip>
      </div>
    </div>
  </div>
</template>
