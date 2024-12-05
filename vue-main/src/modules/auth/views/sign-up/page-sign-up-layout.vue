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
    const lastStep = ref(false);
    const isBack = ref(false); // Состояние для направления

    const handleModelValueChange = (value) => {
      modelValue.value = value;
    };

    const handleLastStepEvent = (value) => {
      lastStep.value = value;
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
            uploadType: modelValue.value,
          };
        case '/sign-up/step-3':
          return {
            stepNumber: 3,
            back: 'PageSignUpSecond',
            next: 'PageMainLayout',
            lastStep: true,
          };
        default:
          return {};
      }
    });

    const navigate = (direction) => {
      isBack.value = direction === 'back';
    };

    return {
      route,
      stepInfo,
      modelValue,
      handleModelValueChange,
      handleLastStepEvent,
      isBack,
      navigate,
    };
  },
});
</script>

<template>
  <div class="container">
    <!-- <ContainerCard header-text="Регистрация" footer-class="--w-100" :key="stepInfo.stepNumber">
      <template #card-header>
        <ProgressBar :steps-count="3" :stepNumber="stepInfo.stepNumber" :is-back="isBack" />
      </template>
      <template #card-body>
        <router-view
          :modelValue="modelValue"
          @update:modelValue="handleModelValueChange"
          @lastStep="handleLastStepEvent"
          v-slot="{ Component }"
        >
          <transition name="fade" mode="out-in">
            <component :is="Component" :key="route.path" />
          </transition>
        </router-view>
      </template>
      <template #card-footer>
        <router-link :to="{ name: stepInfo.back }" @click.prevent="navigate('back')">
          <div class="arrow-icon">
            <i class="arrow-left"></i>
          </div>
        </router-link>

        <router-link
        class="button button-sm"
          v-if="
            (stepInfo.uploadType !== undefined && stepInfo.uploadType === 'manual') ||
            stepInfo.lastStep
          "
          :to="{ name: stepInfo.next }"
          @click.prevent="navigate('next')"
        >
          Завершить
        </router-link>

        <router-link v-else :to="{ name: stepInfo.next }" @click.prevent="navigate('next')">
          <div class="arrow-icon">
            <i class="arrow-right"></i>
          </div>
        </router-link>
      </template>
    </ContainerCard> -->
    <router-view></router-view>
  </div>
</template>
