<script>
import { defineComponent, computed, ref } from 'vue';
import { useRoute } from 'vue-router';
import ProgressBar from '@/components/ProgressBar.vue';
import ContainerCard from '@/components/ContainerCard.vue';

export default defineComponent({
  name: 'PageSignUpLayout',
  components: { ProgressBar, ContainerCard },
  setup() {
    const route = useRoute();
    const modelValue = ref(null);

    const handleModelValueChange = (value) => {
      modelValue.value = value;
    };

    const stepInfo = computed(() => {
      switch (route.path) {
        case '/sign-up/step-1':
          return { stepNumber: 1, back: 'PageGreetings', next: 'PageSignUpSecond' };
        case '/sign-up/step-2':
          return {
            stepNumber: 2,
            back: 'PageSignUpFirst',
            next: modelValue.value === 'manual' ? 'PageMainLayout' : 'PageSignUpThird',
            uploadType: modelValue.value
          };
        case '/sign-up/step-3':
          return { stepNumber: 3, back: 'PageSignUpSecond', next: 'PageSignUpFourth' };
        default:
          return {};
      }
    });

    return {
      stepInfo,
      modelValue,
      handleModelValueChange,
    };
  },
});
</script>

<template>
  <div class="container">
    <ContainerCard header-text="Регистрация" footer-class="--w-100" :key="stepInfo.stepNumber">
      <template #card-header>
        <ProgressBar :steps-count="3" :stepNumber="stepInfo.stepNumber" />
      </template>
      <template #card-body>
        <router-view :modelValue="modelValue" @update:modelValue="handleModelValueChange" />
      </template>
      <template #card-footer>
        <router-link
          :to="{
            name: stepInfo.back,
          }"
        >
          <div class="arrow-icon">
            <i class="arrow-left"></i>
          </div>
        </router-link>
        <router-link
          class="button"
          v-if="stepInfo.uploadType !== undefined && stepInfo.uploadType === 'manual'"
          :to="{
            name: stepInfo.next,
          }"
        >
          Завершить
        </router-link>

        <router-link
          v-else
          :to="{
            name: stepInfo.next,
          }"
        >
          <div class="arrow-icon">
            <i class="arrow-right"></i>
          </div>
        </router-link>
      </template>
    </ContainerCard>
  </div>
</template>
