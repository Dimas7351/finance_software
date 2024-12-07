<script>
import Selector from '@/components/Selector.vue';
import Tooltip from '@/components/Tooltip.vue';
import ContainerCard from '@/components/ContainerCard.vue';
import ProgressBar from '@/components/ProgressBar.vue';
import AuthService from '@/modules/auth/service/auth.service';
import { smallNotification } from '@/services/notification-service';
import { useRouter } from 'vue-router';
import { defineComponent, ref } from 'vue';
import { useStore } from 'vuex';
import router from '@/router';

export default defineComponent({
  name: 'PageSignUpThird',
  components: { Tooltip, Selector, ContainerCard, ProgressBar },
  props: {
    modelValue: {
      type: [String, Number, Boolean],
      default: null,
    },
  },

  setup() {
    const store = useStore();
    const form = ref({
      bankName: store.getters.getStepThree?.bankName || 'TINKOFF',
      phone: store.getters.getStepThree?.phoneNumber || '',
    });

    const stepInfo = ref({
      stepNumber: 3,
      back: 'PageSignUpSecond',
      next: 'PageBudget',
      lastStep: true,
    });

    const onNext = () => {
      if (form.value.bankName && form.value.phone) {
        AuthService.signUpSendStepThreeInfo({
          bankName: form.value.bankName,
          phoneNumber: form.value.phone,
        }).then(() => {
          router.push({ name: stepInfo.value.next });
        });
      } else {
        smallNotification('danger', { title: 'Заполните все поля!' });
      }
    };

    // Phone Mask
    const prefixNumber = (str) => {
      if (str === '7') {
        return '7 (';
      }
      if (str === '8') {
        return '8 (';
      }
      if (str === '9') {
        return '7 (9';
      }
      return '7 (';
    };

    const numberLength = 11;

    const formatPhone = () => {
      if (!form.value.phone) return;

      let value = form.value.phone.replace(/\D/g, '');
      let result;

      if (form.value.phone.includes('+8') || form.value.phone[0] === '8') {
        result = '';
      } else {
        result = '+';
      }

      for (let i = 0; i < value.length && i < numberLength; i++) {
        switch (i) {
          case 0:
            result += prefixNumber(value[i]);
            continue;
          case 4:
            result += ') ';
            break;
          case 7:
            result += '-';
            break;
          case 9:
            result += '-';
            break;
          default:
            break;
        }
        result += value[i];
      }

      form.value.phone = result;
    };

    return {
      form,
      stepInfo,
      formatPhone,
      onNext,
    };
  },
});
</script>

<template>
  <ContainerCard header-text="Регистрация" footer-class="--w-100" :key="stepInfo.stepNumber">
    <template #card-header>
      <ProgressBar :steps-count="3" :stepNumber="stepInfo.stepNumber" />
    </template>
    <template #card-body>
      <div style="width: 600px" class="form-group --flex-row">
        <label class="--mb-0 --text-nowrap">Статистику за какой период вы хотите получить?</label>
        <Selector
          class="--ml-4"
          selector-width="156px"
          :options="[
            { title: '3 месяца', value: 3 },
            { title: '6 месяцев', value: 6 },
            { title: '12 месяцев', value: 6 },
          ]"
          :model-value="{ title: '3 месяца', value: 3 }"
        />
      </div>
      <div style="width: 400px">
        <div class="form-group">
          <label class="radio-group --flex-row" for="TINKOFF">
            <input
              type="radio"
              id="TINKOFF"
              v-model="form.bankName"
              value="TINKOFF"
              name="bankName"
            />
            <div class="bank-logo --tinkoff"></div>
            Т-БАНК
          </label>
        </div>
        <div class="form-group">
          <label class="radio-group --flex-row" for="SBER">
            <input type="radio" id="SBER" v-model="form.bankName" value="SBER" name="bankName" />
            <div class="bank-logo --sber"></div>
            СБЕР
          </label>
        </div>
        <div class="form-group">
          <label class="radio-group --flex-row" for="ALFA">
            <input type="radio" id="ALFA" v-model="form.bankName" value="ALFA" name="bankName" />
            <div class="bank-logo --alfa"></div>
            Альфа-Банк
          </label>
        </div>
        <div class="form-group">
          <label class="radio-group --flex-row" for="VTB">
            <input type="radio" id="VTB" v-model="form.bankName" value="VTB" name="bankName" />
            <div class="bank-logo --vtb"></div>
            ВТБ
          </label>
        </div>
      </div>
      <div class="form-group">
        <div class="--flex-row">
          <label class="--mb-0 --text-nowrap"
            >Номер телефона, к которому привязан нужный счет:</label
          >
          <input
            type="text"
            inputmode="tel"
            placeholder="+7 (XXX) XXX-XXX-XX"
            class="form-input form-input-phone --ml-4"
            @input="formatPhone"
            v-model="form.phone"
          />
        </div>
      </div>
    </template>
    <template #card-footer>
      <router-link :to="{ name: stepInfo.back }">
        <div class="arrow-icon">
          <i class="arrow-left"></i>
        </div>
      </router-link>

      <div @click.prevent="onNext">
        <div class="arrow-icon">
          <i class="arrow-right"></i>
        </div>
      </div>
    </template>
  </ContainerCard>
</template>
