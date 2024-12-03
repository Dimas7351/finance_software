<script>
import ContainerCard from '@/components/ContainerCard.vue';
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'PagePasswordRecovery',
  components: { ContainerCard },
  setup() {
    const form = ref({
      email: '',
    });

    return {
      form,
    };
  },
});
</script>

<template>
  <div class="container">
    <ContainerCard header-text="Восстановление пароля" style="height: 344px">
      <template #card-body>
        <div class="--mb-3">
          Введите адрес электронной почты, к которому привязан аккаунт. Все инструкции по
          восстановлению доступа к аккаунту будут в отправленном письме
        </div>
        <div class="--w-100 --justify-content-center">
          <div style="width: 344px">
            <input type="text" placeholder="example@gmail.com" class="form-input --text-center" v-model="form.email" />
          </div>
        </div>
      </template>
      <template #card-footer>
        <router-link class="button button-sm" :to="{ name: 'PagePasswordRecoveryRefresh' }">
          Отправить
        </router-link>
      </template>
    </ContainerCard>
  </div>
</template>
