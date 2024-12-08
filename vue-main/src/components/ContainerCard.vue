<script>
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'ContainerCard',
  props: {
    headerText: {
      type: String,
      default: null,
    },
    headerClass: {
      type: String,
      default: null,
    },
    bodyClass: {
      type: String,
      default: null,
    },
    footerClass: {
      type: String,
      default: null,
    },
    cardClass: {
      type: String,
      default: null
    }
  },
  setup() {},
});
</script>

<template>
  <div class="card" :class="cardClass">
    <div class="card__header --user-select-none" :class="headerClass">
      <span class="card__header__text" v-if="headerText">{{ headerText }}</span>
      <slot name="card-header"></slot>
    </div>

    <div class="card__body" :class="bodyClass">
      <slot name="card-body"></slot>
    </div>

    <div class="card__footer" :class="footerClass">
      <slot name="card-footer"></slot>
    </div>
  </div>
</template>
