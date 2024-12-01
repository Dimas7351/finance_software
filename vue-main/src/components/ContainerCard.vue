<script>
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'ContainerCard',
  props: {
    headerText: {
      type: String,
      default: null,
    },
  },
  setup() {},
});
</script>

<template>
  <div class="card">
    <div class="card__header --user-select-none">
      <span class="card__header__text" v-if="headerText">{{ headerText }}</span>
      <slot name="card-header"></slot>
    </div>

    <div class="card__body">
      <slot name="card-body"></slot>
    </div>
    <div class="card__footer">
      <slot name="card-footer"></slot>
    </div>
  </div>
</template>
