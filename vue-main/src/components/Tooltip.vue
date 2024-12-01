<template>
  <div
    @mouseover="openTooltip"
    class="tooltip"
    :style="{
      '--tooltip-width': width,
      '--tooltip-max-width': maxWidth,
      '--tooltip-font-size': tooltipFontSize
    }"
  >
    <div v-if="$slots.element" class="tooltip__element">
      <slot name="element" />
    </div>
    <div v-else-if="elementHtml" class="tooltip__element" v-html="elementHtml"></div>
    <div
      ref="tooltip"
      v-if="$slots.content"
      class="tooltip__tooltip"
      :class="['-' + position, { '--disable-mobile': !showOnMobileVersion }]"
    >
      <div>
        <slot name="content" />
      </div>
    </div>
    <div
      v-else-if="contentHtml"
      class="tooltip__tooltip"
      v-html="contentHtml"
      :class="'-' + position"
    ></div>
  </div>
</template>

<script>
import { ref, watch, onMounted, onBeforeUnmount } from 'vue';

export default {
  name: 'GfCustomTooltip',
  props: {
    position: {
      type: String,
      default: 'top',
    },
    width: {
      type: String,
      default: 'auto',
    },
    maxWidth: {
      type: String,
      default: '350px',
    },
    elementHtml: {
      type: String,
      default: null,
    },
    contentHtml: {
      type: String,
      default: null,
    },
    showOnMobileVersion: {
      type: Boolean,
      default: false,
    },
    container: {
      type: String,
      default: 'body',
    },
    ignoreIntersection: {
      type: Boolean,
      default: false,
    },
    tooltipFontSize: {
      type: String,
      default: '14px',
    },
  },
  setup(props) {
    const tooltipRef = ref(null);
    let observer;

    const getIntersection = ([{ isIntersecting, target, boundingClientRect }]) => {
      if (props.ignoreIntersection) return;
      if (isIntersecting) {
        const containerElement = document.querySelector(props.container);
        if (!containerElement) return;
        const containerRect = containerElement.getBoundingClientRect();
        if (boundingClientRect.left < containerRect.left) {
          if (!target.classList.contains('-right')) {
            target.classList.replace('-' + props.position, '-right');
          }
        } else if (boundingClientRect.right > containerRect.right) {
          if (!target.classList.contains('-left')) {
            target.classList.replace('-' + props.position, '-left');
          }
        } else if (boundingClientRect.top < containerRect.top) {
          if (!target.classList.contains('-bottom')) {
            target.classList.replace('-' + props.position, '-bottom');
          }
        } else if (boundingClientRect.bottom > containerRect.bottom) {
          if (!target.classList.contains('-top')) {
            target.classList.replace('-' + props.position, '-top');
          }
        }
      }
    };

    const openTooltip = () => {
      if (props.ignoreIntersection || !tooltipRef.value) return;
      observer.observe(tooltipRef.value);
      getIntersection([
        {
          isIntersecting: true,
          target: tooltipRef.value,
          boundingClientRect: tooltipRef.value.getBoundingClientRect(),
        },
      ]);
    };

    onMounted(() => {
      observer = new IntersectionObserver(getIntersection);
    });

    onBeforeUnmount(() => {
      observer.disconnect();
    });

    return {
      tooltipRef,
      openTooltip,
    };
  },
};
</script>

<style>
.tooltip {
  position: relative;
  width: max-content;
  --tooltip-arrow-size: 4.3px;
  --tooltip-margin: 7px;
}

.tooltip__element {
  cursor: pointer;
}

.tooltip__tooltip {
  position: absolute;
  padding: 0.5rem;
  width: var(--tooltip-width);
  border: 1px solid var(--gfd-bg);
  max-width: var(--tooltip-max-width);
  max-height: 500px;
  opacity: 0;
  cursor: default;
  background-color: var(--gfd-bg);
  box-shadow: 0 0 2px 1px var(--gfd-bs);
  border-radius: 5px;
  z-index: 1020;
  pointer-events: none;
  backdrop-filter: saturate(180%) blur(18px);
  transition:
    clip-path 0.3s ease,
    opacity 0.3s ease;
  clip-path: inset(0 100% 100% 100% round 5px);
  font-size: var(--tooltip-font-size);
}

.tooltip__tooltip > :first-child {
  text-overflow: ellipsis;
  overflow: hidden;
}

.tooltip__tooltip.--disable-mobile {
  @media screen and (max-width: 767px) {
    display: none;
  }
}

.tooltip__tooltip.-top {
  bottom: 100%;
  left: 50%;
  margin-bottom: var(--tooltip-margin);
  transform: translateX(-50%) translateY(3px);
}

.tooltip__tooltip.-bottom {
  left: 50%;
  margin-top: var(--tooltip-margin);
  transform: translateX(-50%) translateY(-3px);
}

.tooltip__tooltip.-left {
  top: 50%;
  left: calc(-1 * var(--tooltip-margin));
  transform: translateX(calc(-100% + 3px)) translateY(-50%);
}

.tooltip__tooltip.-right {
  top: 50%;
  right: calc(-1 * var(--tooltip-margin));
  transform: translateX(calc(100% - 3px)) translateY(-50%);
}

.tooltip__element:hover + .tooltip__tooltip,
.tooltip__tooltip:hover {
  clip-path: inset(-7px -7px -7px -7px round var(--gfd-br, 0.4rem));
  pointer-events: all;
  opacity: 1;
}

.tooltip__element:hover + .tooltip__tooltip {
  pointer-events: all;
}
</style>
