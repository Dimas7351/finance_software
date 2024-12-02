<script>
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'PageSignUpSecond',
  props: {
    modelValue: {
      type: [String, Number, Boolean],
      default: null,
    },
  },
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    const form = ref({
      preferedCurrency: 'RUB',
      uploadType: 'fromBank',
    });

    const onUploadTypeInput = () => {
      emit('update:modelValue', form.value.uploadType);
    };

    return {
      form,
      onUploadTypeInput,
    };
  },
});
</script>

<template>
  <div>
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
  </div>
</template>
