<script>
import { defineComponent, ref } from 'vue';
export default defineComponent({
  name: 'TheHeader',
  props: {
    activeCurrency: {
      type: String,
    },
  },
  emits: ['currency-change'],
  setup(props, { emit }) {
    const setCurrency = (currency) => {
      emit('currency-change', currency);
    };

    const currencies = ref([
      {
        key: 'RUB',
        icon: 'icon-rub',
      },
      {
        key: 'USD',
        icon: 'icon-usd',
      },
      {
        key: 'EUR',
        icon: 'icon-euro',
      },
      {
        key: 'GBP',
        icon: 'icon-gbp',
      },
    ]);

    return {
      setCurrency,
      currencies,
    };
  },
});
</script>

<template>
  <header class="header-wrapper">
    <div class="currencies">
      <div
        class="currencies-item"
        v-for="currency in currencies"
        :class="{ active: activeCurrency === currency.key }"
        @click="setCurrency(currency.key)"
      >
        <i class="icon" :class="currency.icon"></i>
      </div>
    </div>
    <div class="header-actions">
      <div class="header-actions__wrapper">
        <i class="icon icon-account_circle"></i>
        <span class="header-actions__username"> Test User </span>
      </div>
      <i class="icon icon-logout"></i>
    </div>
  </header>
</template>
