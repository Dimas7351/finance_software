<script>
import ContainerCard from '@/components/ContainerCard.vue';
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'PageGreetings',
  components: { ContainerCard },
  setup() {},
});
</script>

<template>
  <div class="container">
    <ContainerCard header-text="Здравствуйте!" card-class="--greetings" footer-class="--greetings">
      <template #card-body>
        <span class="--user-select-none">
          Рады приветствовать вас в приложении FinanceAN! <br>Здесь вы сможете получить детальную аналитику по
          своим банковским аккаунтам, получить прогнозы на текущий месяц, советы по экономии и
          другое. Приступим!
        </span>
      </template>

      <template #card-footer>
        <div class="--greetings">
          <router-link class="button button-sm" to="/sign-in">Вход</router-link>
          <router-link
            class="button button-sm"
            :to="{
              name: 'PageSignUpFirst',
            }"
          >
            Регистрация
          </router-link>
        </div>
      </template>
    </ContainerCard>
  </div>
</template>
