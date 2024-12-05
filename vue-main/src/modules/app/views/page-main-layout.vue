<script>
import TheBanner from '@/components/TheBanner.vue';
import TheHeader from '@/components/TheHeader.vue';
import { useRoute } from 'vue-router';
import { ref } from 'vue';

export default {
  name: 'PageMainLayout',
  props: {},
  components: {
    TheHeader,
    TheBanner,
  },
  setup() {
    const route = useRoute();
    const activeCurrency = ref('RUB');

    const setActiveCurrency = (currency) => {
      activeCurrency.value = currency;
    };

    return {
      activeCurrency,
      setActiveCurrency,
    };
  },
};
</script>

<template>
  <div>
    <TheHeader :activeCurrency="activeCurrency" @currency-change="setActiveCurrency" />
    <TheBanner></TheBanner>
    <div class="app-container">
      <router-view></router-view>
    </div>
  </div>
</template>
