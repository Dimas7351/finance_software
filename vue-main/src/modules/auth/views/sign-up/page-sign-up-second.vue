<script>
import ContainerCard from '@/components/ContainerCard.vue';
import ProgressBar from '@/components/ProgressBar.vue';
import AuthService from '@/modules/auth/service/auth.service';
import { smallNotification } from '@/services/notification-service';
import { useRouter } from 'vue-router';
import { defineComponent, ref, computed } from 'vue';
import { useStore } from 'vuex';

export default defineComponent({
  name: 'PageSignUpSecond',
  components: { ContainerCard, ProgressBar },
  setup() {
    const router = useRouter();
    const store = useStore();

    const form = ref({
      preferedCurrency: store.getters.getStepTwo?.currency || 'RUB',
      uploadType: store.getters.getStepTwo?.uploadType || 'fromBank',
    });

    const direction = ref('');

    const stepInfo = computed(() => {
      return {
        stepNumber: 2,
        back: 'PageSignUpFirst',
        next: form.value.uploadType === 'manual' ? 'PageMainLayout' : 'PageSignUpThird',
      };
    });

    const onNext = () => {
      direction.value = 'next';
      AuthService.signUpSendStepTwoInfo({
        currency: form.value.preferedCurrency,
        uploadType: form.value.uploadType,
      }).then(() => {
        router.push({ name: stepInfo.value.next });
      });
    };

    const onBack = () => {
      direction.value = 'back';
      router.push({ name: stepInfo.value.back });
    };

    return {
      form,
      stepInfo,
      direction,
      onNext,
      onBack,
    };
  },
});
</script>

<template>
  <ContainerCard header-text="Регистрация" footer-class="--w-100" :key="stepInfo.stepNumber">
    <template #card-header>
      <ProgressBar :steps-count="3" :stepNumber="stepInfo.stepNumber" :is-back="direction" />
    </template>
    <template #card-body>
      <div style="width: 400px">
        <div class="form-group">
          <label>Выберите основную валюту аккаунта:</label>
          <label class="radio-group --flex-row" for="currency-rub">
            <input
              type="radio"
              id="currency-rub"
              v-model="form.preferedCurrency"
              value="RUB"
              name="currency"
            />
            Рубль (₽)
          </label>
        </div>

        <div class="form-group">
          <label class="radio-group --flex-row" for="currency-usd">
            <input
              type="radio"
              id="currency-usd"
              v-model="form.preferedCurrency"
              value="USD"
              name="currency"
            />
            USD ($)
          </label>
        </div>

        <div class="form-group">
          <label class="radio-group --flex-row" for="currency-eur">
            <input
              type="radio"
              id="currency-eur"
              v-model="form.preferedCurrency"
              value="EUR"
              name="currency"
            />
            EUR (€)
          </label>
        </div>

        <div class="form-group">
          <label class="radio-group --flex-row" for="currency-gbp">
            <input
              type="radio"
              id="currency-gbp"
              v-model="form.preferedCurrency"
              value="GBP"
              name="currency"
            />
            GBP (£)
          </label>
        </div>
      </div>

      <div>
        <div class="--text-left" style="margin-top: 50px; margin-bottom: 20px; font-size: 16px">
          Собираетесь ли вы привязывать банковский счет? Вы можете пропустить этот пункт сейчас и
          сделать это в приложении
        </div>

        <div style="width: 400px">
          <div class="form-group">
            <label class="radio-group --flex-row" for="uploadBankHistory">
              <input
                type="radio"
                value="fromBank"
                id="uploadBankHistory"
                name="uploadType"
                v-model="form.uploadType"
                @change="onUploadTypeInput"
              />
              Загружаем историю из банков
            </label>
          </div>

          <div class="form-group">
            <label class="radio-group --flex-row" for="manual">
              <input
                type="radio"
                id="manual"
                name="uploadType"
                value="manual"
                v-model="form.uploadType"
                @change="onUploadTypeInput"
              />
              Буду вести учет вручную с нуля
            </label>
          </div>
        </div>
      </div>
    </template>
    <template #card-footer>
      <router-link :to="{ name: stepInfo.back }">
        <div class="arrow-icon">
          <i class="arrow-left"></i>
        </div>
      </router-link>

      <div
        @click.prevent="onNext"
        class="button button-sm"
        v-if="form.uploadType !== undefined && form.uploadType === 'manual'"
      >
        Завершить
      </div>

      <div v-else @click.prevent="onNext">
        <div class="arrow-icon">
          <i class="arrow-right"></i>
        </div>
      </div>
    </template>
  </ContainerCard>
</template>
