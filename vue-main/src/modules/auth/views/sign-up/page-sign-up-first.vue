<script>
import ContainerCard from '@/components/ContainerCard.vue';
import ProgressBar from '@/components/ProgressBar.vue';
import Tooltip from '@/components/Tooltip.vue';
import AuthService from '@/modules/auth/service/auth.service';
import { smallNotification } from '@/services/notification-service';
import { defineComponent, ref } from 'vue';
import { useRouter } from 'vue-router';

export default defineComponent({
  name: 'PageSignUpFirst',
  components: { ContainerCard, ProgressBar, Tooltip },
  setup() {
    const router = useRouter();

    const stepInfo = ref({
      stepNumber: 1,
      back: 'PageGreetings',
      next: 'PageSignUpSecond',
    });

    const form = ref({
      name: '',
      email: '',
      login: '',
      password: '',
      passwordConfirm: '',
    });

    const onNext = () => {
      if (
        form.value.name &&
        form.value.email &&
        form.value.login &&
        form.value.password &&
        form.value.passwordConfirm
      ) {
        AuthService.signUpSendStepOneInfo({ ...form.value }).then(() => {
          router.push({ name: stepInfo.value.next });
        });
      } else {
        smallNotification('danger', { title: 'Заполните все поля!' });
      }
    };

    return {
      form,
      stepInfo,
      onNext,
    };
  },
});
</script>

<template>
  <ContainerCard header-text="Регистрация" footer-class="--w-100" :key="stepInfo.stepNumber">
    <template #card-header>
      <ProgressBar :steps-count="3" :stepNumber="stepInfo.stepNumber" />
    </template>
    <template #card-body>
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
                Будет использоваться для вашей идентификации в системе. Цифры и латиница, >6
                символов
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
              <template #content>
                Введите пароль еще раз, чтобы избежать ошибки при вводе
              </template>
            </Tooltip>
          </div>
        </div>
      </div>
    </template>
    <template #card-footer>
      <router-link :to="{ name: stepInfo.back }">
        <div class="arrow-icon">
          <i class="arrow-left"></i>
        </div>
      </router-link>

      <div @click.prevent="onNext">
        <div class="arrow-icon">
          <i class="arrow-right"></i>
        </div>
      </div>
    </template>
  </ContainerCard>
</template>
