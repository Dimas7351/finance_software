<script>
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'ProgressBar',
  props: {
    stepsCount: {
      type: Number,
      default: 3,
    },
    stepNumber: {
      type: Number,
      default: 0,
    },
  },
  setup(props) {
    const stepsCount = props.stepsCount;

    const stepNumber = props.stepNumber;

    const isActive = (activeStep) => {
      return activeStep <= Number(stepNumber);
    };

    return {
      stepNumber,
      stepsCount,
      isActive,
    };
  },
});
</script>

<template>
  <div
    class="progress-bar"
    :style="{
      '--steps-count': stepsCount,
      '--current-step': stepNumber,
      '--progress': `${((stepNumber - 1) / (stepsCount - 1)) * 100}%`,
    }"
  >
    <div
      v-for="step in stepsCount"
      class="progress-bar__status"
      :class="{ '--active': isActive(step) }"
    ></div>
  </div>
</template>

<style scoped>
.progress-bar {
  --progress: calc(var(--current-step) / var(--steps-count) * 100);
  transition: background-color 0.3s ease-in-out;
  background: linear-gradient(
    to right,
    var(--color-primary) 0,
    var(--color-primary) var(--progress),
    var(--color-background-disabled) var(--progress)
  );
}
</style>
