<script>
import ContainerCard from '@/components/ContainerCard.vue';
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'PagePasswordRecoveryRefresh',
  components: { ContainerCard },
  setup() {
    const form = ref({
      newPassword: '',
      newPasswordConfirm: '',
    });

    return {
      form,
    };
  },
});
</script>

<template>
  <div class="container">
    <ContainerCard header-text="Новый пароль" style="height: 344px;">
      <template #card-body>
        <div style="width: 373px">
          <div class="form-group">
            <label>Новый пароль</label>
            <input class="form-input" />
          </div>
          <div class="form-group">
            <label>Повторите пароль</label>
            <input class="form-input" />
          </div>
        </div>
      </template>
      <template #card-footer>
        <router-link class="button button-sm" :to="{ name: 'PageMainLayout' }"> Подтвердить </router-link>
      </template>
    </ContainerCard>
  </div>
</template>
