<script>
import { defineComponent } from 'vue';
import { useRoute } from 'vue-router';
import Dropdown from '@/components/Dropdown.vue';

export default defineComponent({
  name: 'TheBanner',
  components: { Dropdown },
  setup() {
    const route = useRoute(); // Используем реактивный объект route напрямую

    const dropdownItems = [
      { icon: 'icon-account_balance_wallet', title: 'Бюджеты', name: 'PageBudget' },
      { icon: 'icon-credit_card', title: 'Карты', name: 'PageCards' },
      { icon: 'icon-target', title: 'Цели', name: 'PageGoals' },
      { icon: 'icon-usd', title: 'Операции', name: 'PageOperations' },
      { icon: 'icon-settings', title: 'Настройки', name: 'PageSettings' },
    ];

    return {
      dropdownItems,
      route, // возвращаем объект route, чтобы можно было использовать его в шаблоне
    };
  },
});
</script>

<template>
  <div class="banner-wrapper">
    <div class="app-info">
      <i class="icon icon-app-logo" style="font-weight: 900; font-size: 41px"></i>
      <span class="app-info__title">FinanceAN</span>
    </div>
    <Dropdown btn-icon="icon icon-lists">
      <router-link
        :to="{ name: item.name }"
        :class="{ active: route.name === item.name }"
        class="dropdown-item"
        v-for="item in dropdownItems"
        :key="item.name"
      >
        <i class="icon" :class="item.icon"></i>
        {{ item.title }}
      </router-link>
    </Dropdown>
  </div>
</template>
