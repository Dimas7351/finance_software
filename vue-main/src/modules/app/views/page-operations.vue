<script>
import { useRoute } from 'vue-router';
import { ref } from 'vue';

export default {
  name: 'PageOperations',
  props: {},
  setup() {
    const route = useRoute();
  },
};
</script>

<template>
  <div class="--in-work">==== В разработычах ====</div>
</template>
