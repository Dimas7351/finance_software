<script>
import Tooltip from '@/components/Tooltip.vue';
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'PageSignUpThird',
  components: { Tooltip },
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
  Third
</template>
