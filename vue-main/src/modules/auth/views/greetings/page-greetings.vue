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
    <ContainerCard header-text="Привет!">
      <template #card-body>
        <span class="--user-select-none">
          <h3>Рады приветствовать вас в приложении BeBro!</h3>
          <br />
          Здесь вы сможете получить детальную аналитику по своим банковским аккаунтам, получить
          прогнозы на текущий месяц, советы по экономии и другое
        </span>
      </template>

      <template #card-footer>
        <div class="--greetings">
          <router-link class="button" to="/sign-in">Вход</router-link>
          <router-link
            class="button"
            :to="{
              path: '/sign-up',
              query: { step: 1 },
            }"
            >Регистрация</router-link
          >
        </div>
      </template>
    </ContainerCard>
  </div>
</template>
