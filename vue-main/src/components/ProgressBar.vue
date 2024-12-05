<script>
import { defineComponent, computed, watchEffect, ref } from 'vue';

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
    isBack: {
      type: Boolean,
      default: false,
    },
  },
  setup(props) {
    const stepNumber = computed(() => props.stepNumber);
    const stepsCount = props.stepsCount;
    const isTransitionBackwards = props.isBack;

    const progress = computed(() => {
      return ((stepNumber.value - 1) / (stepsCount - 1)) * 100;
    });

    const previous = computed(() => {
      if (stepNumber.value <= 1) {
        return 0;
      }
      return ((stepNumber.value - 2) / (stepsCount - 1)) * 100;
    });

    const next = computed(() => {
      return (stepNumber.value / (stepsCount - 1)) * 100;
    });

    const isActive = (activeStep) => {
      return activeStep <= stepNumber.value;
    };

    return {
      stepsCount,
      stepNumber,
      progress,
      previous,
      next,
      isActive,
      isTransitionBackwards,
    };
  },
});
</script>

<template>
  <div
    class="progress-bar"
    :style="{
      '--steps-count': stepsCount,
      '--progress': `${progress}%`,
      '--previous': `${previous}%`,
      '--next': `${next}%`,
      '--status-width': isTransitionBackwards ? `${previous}%` : `${progress}%`,
    }"
  >
    <div
      class="progress-bar__track"
      :class="{ 'next-animation': !isTransitionBackwards, 'back-animation': isTransitionBackwards }"
    ></div>
    <div
      v-for="step in stepsCount"
      :key="step"
      class="progress-bar__status"
      :class="{
        '--active': isActive(step),
      }"
    ></div>
  </div>
</template>

<style scoped>
.progress-bar {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 12px;
  border-radius: 10px;
  background: var(--color-background-disabled);
  transition: background-color 0.5s;
}

.progress-bar__track {
  --start-position: calc(var(--progress) - var(--previous));
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  background: var(--color-primary);
  border-radius: 10px;
  width: var(--progress);
  z-index: 1;
  animation-duration: 0.5s;
}

.next-animation {
  animation-name: next;
}

.back-animation {
  animation-name: back;
}

@keyframes next {
  from {
    width: var(--previous);
  }

  to {
    width: var(--progress);
  }
}

@keyframes back {
  from {
    width: var(--next);
  }

  to {
    width: var(--progress);
  }
}

.progress-bar__status {
  position: relative;
  width: 18px;
  height: 18px;
  border: 2px solid var(--white);
  border-radius: 100%;
  z-index: 100;
  background-color: var(--color-background-disabled);
  transition:
    background-color 0.3s ease-in-out,
    border 0.3s;
}

.progress-bar__status.--active {
  background-color: var(--color-primary);
}
</style>
