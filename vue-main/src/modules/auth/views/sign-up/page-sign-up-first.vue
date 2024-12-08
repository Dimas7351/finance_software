<script>
import { defineComponent, ref } from 'vue';
import { useStore } from 'vuex';
import { useRouter, useRoute } from 'vue-router';
import ContainerCard from '@/components/ContainerCard.vue';
import ProgressBar from '@/components/ProgressBar.vue';
import Tooltip from '@/components/Tooltip.vue';
import AuthService from '@/modules/auth/service/auth.service';
import { smallNotification } from '@/services/notification-service';

export default defineComponent({
  name: 'PageSignUpFirst',
  components: { ContainerCard, ProgressBar, Tooltip },
  setup() {
    const router = useRouter();
    const route = useRoute();

    const stepInfo = ref({
      stepNumber: 1,
      back: 'PageGreetings',
      next: 'PageSignUpSecond',
    });

    const store = useStore();

    const form = ref({
      name: store.getters.getStepOne?.name || '',
      email: store.getters.getStepOne?.email || '',
      login: store.getters.getStepOne?.login || '',
      password: store.getters.getStepOne?.password || '',
      passwordConfirm: store.getters.getStepOne?.passwordConfirm || '',
    });

    let errors = ref({});

    const direction = ref('');

    const onNext = () => {
      if (
        form.value.name &&
        form.value.email &&
        form.value.login &&
        form.value.password &&
        form.value.passwordConfirm &&
        form.value.password === form.value.passwordConfirm
      ) {
        if (
          form.value.password !== form.value.passwordConfirm &&
          form.value.password &&
          form.value.passwordConfirm
        ) {
          smallNotification('danger', { title: 'Пароль и подтверждение пароля не совпадают!' });
          errors.value = {
            passwordConfirm: 'Пароли не совпадают',
          };
          return;
        }
        direction.value = 'next';
        AuthService.signUpSendStepOneInfo({
          name: form.value.name,
          email: form.value.email,
          password: form.value.password,
        }, form.value)
          .then(() => {
            router.push({ name: stepInfo.value.next });
          })
          .catch((err) => {
            console.log(err);
            errors.value = { ...err };
            console.log(errors.value);
          });
      } else {
        smallNotification('danger', { title: 'Заполните все поля!' });
      }
    };

    const onBack = () => {
      direction.value = 'back';
      router.push({ name: stepInfo.value.back });
    };

    const isGoingBack = route.name === stepInfo.value.back;
    const isGoingForward = route.name === stepInfo.value.next;

    return {
      errors,
      form,
      stepInfo,
      direction,
      onNext,
      onBack,
      isGoingBack,
      isGoingForward,
    };
  },
});
</script>

<template>
  <ContainerCard header-text="Регистрация" footer-class="--w-100" :key="stepInfo.stepNumber">
    <template #card-header>
      <ProgressBar :steps-count="3" :stepNumber="stepInfo.stepNumber" :is-back="direction" />
    </template>
    <template #card-body>
      <div style="width: 344px">
        <div class="form-group">
          <label for="name"> Имя </label>
          <div class="--flex-row --w-100">
            <input
              id="name"
              class="form-input"
              :class="{ '--has-error': errors.name }"
              v-model="form.name"
            />
            <Tooltip :tooltip-font-size="'14px'" width="300px" position="right">
              <template #element>
                <span class="question-icon --ml-3">?</span>
              </template>
              <template #content> Будем знать, как к вам обращаться </template>
            </Tooltip>
          </div>
          <div class="--error" v-if="errors.name">
            {{ errors.name }}
          </div>
        </div>
        <div class="form-group">
          <label for="email">Адрес электронной почты</label>
          <div class="--flex-row --w-100">
            <input
              id="email"
              class="form-input"
              :class="{ '--has-error': errors.email }"
              v-model="form.email"
            />
            <Tooltip :tooltip-font-size="'14px'" width="300px" position="right">
              <template #element>
                <span class="question-icon --ml-3">?</span>
              </template>
              <template #content> Используем его для восстановления доступа к аккаунту </template>
            </Tooltip>
          </div>
          <div class="--error" v-if="errors.email">
            {{ errors.email }}
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
            <input
              type="password"
              id="password"
              class="form-input"
              :class="{ '--has-error': errors.password }"
              v-model="form.password"
            />
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
          <div class="--error" v-if="errors.password">
            {{ errors.password }}
          </div>
        </div>
        <div class="form-group">
          <label for="passwordConfirm">Повторите пароль</label>
          <div class="--flex-row --w-100">
            <input
              type="password"
              id="passwordConfirm"
              class="form-input"
              :class="{ '--has-error': errors.passwordConfirm }"
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
          <div class="--error" v-if="errors.passwordConfirm">
            {{ errors.passwordConfirm }}
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
