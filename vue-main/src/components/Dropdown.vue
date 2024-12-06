<script>
import { ref, onMounted, onBeforeUnmount } from 'vue';

export default {
  name: 'Dropdown',
  props: {
    position: {
      type: String,
      default: 'left',
      validator: (value) => ['left', 'right'].includes(value),
    },
    btnClass: {
      type: String,
      default: '',
    },
    btnText: {
      type: String,
    },
    btnIcon: {
      type: String,
      default: 'icon icon-lists',
    },
    btnImg: {
      type: String,
    },
    noCarret: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  setup() {
    const showDropdown = ref(false);
    const observer = ref(null);
    const dropdown = ref(null);
    const button = ref(null);
    const wrapper = ref(null);

    const toggle = () => {
      showDropdown.value = !showDropdown.value;
      if (showDropdown.value) {
        observer.value.observe(wrapper.value);
      } else {
        observer.value.disconnect();
      }
    };

    const close = () => {
      showDropdown.value = false;
    };

    const closeOnAway = (event) => {
      if (!event) return;
      if (
        event.target !== dropdown.value &&
        event.target !== button.value &&
        !event.target.closest('.dropdown__body')
      ) {
        close();
      }
    };

    const getIntersection = ([{ isIntersecting, target, boundingClientRect }]) => {
      if (isIntersecting) {
        if (boundingClientRect.left < 0) {
          if (target.classList.contains('--right')) {
            target.classList.replace('--right', '--left');
          }
        } else if (boundingClientRect.right > document.body.offsetWidth) {
          if (target.classList.contains('--left')) {
            target.classList.replace('--left', '--right');
          }
        }
      }
    };

    onMounted(() => {
      observer.value = new IntersectionObserver(getIntersection);
      document.addEventListener('click', closeOnAway);
    });

    onBeforeUnmount(() => {
      document.removeEventListener('click', closeOnAway);
      observer.value.disconnect();
    });

    return {
      showDropdown,
      toggle,
      close,
      closeOnAway,
      getIntersection,
      dropdown,
      button,
      wrapper,
      btnClass: 'dropdown-button',
      position: 'center',
      noCarret: false,
      disabled: false,
      btnIcon: 'icon icon-lists',
      btnImg: null,
      btnText: '',
    };
  },
};
</script>

<template>
  <div
    ref="dropdown"
    class="dropdown"
    :class="{ '--opened': showDropdown, '--no-carret': noCarret }"
  >
    <button
      ref="button"
      class="dropdown__toggle"
      :class="btnClass"
      type="button"
      :disabled="disabled"
      @click="toggle"
    >
      <i v-if="btnIcon" :class="btnIcon"></i>
      <img v-if="btnImg" :src="btnImg" loading="lazy" />
      {{ btnText }}
    </button>
    <div ref="wrapper" class="dropdown__wrapper" :class="'--' + position">
      <div class="dropdown__body">
        <slot />
      </div>
    </div>
  </div>
</template>

<style>
.dropdown {
  position: relative;
  display: block !important;
}

.dropdown-item {
  display: flex;
  align-items: center;
  width: 200px;
  padding: 4px 12px;
  margin-bottom: 3px;
  clear: both;
  font-weight: 400;
  font-size: 17px;
  color: var(--color-text);
  text-align: inherit;
  white-space: nowrap;
  background-color: transparent;
  border: 0;
  border-radius: 0.4rem;
  gap: 10px;
  user-select: none;
}

.dropdown-item .active {
  background-color: var(--dropdown-item-active-bg);
}

.dropdown-item .icon {
  font-size: 24px;
}

.dropdown-item:hover,
.dropdown-item:focus {
  color: var(--color-text) !important;
  text-decoration: none;
  background-color: var(--dropdown-item-active-bg);
}

.dropdown-item.active,
.dropdown-item:active {
  color: var(--color-text);
  text-decoration: none;
  background-color: var(--dropdown-item-active-bg);
}

.dropdown-item.disabled,
.dropdown-item:disabled {
  color: #adb5bd;
  pointer-events: none;
  background-color: transparent;
}

.dropdown__wrapper {
  position: absolute;
  top: 60px;
  padding: 8px;
  background: var(--white);
  border-radius: 6.4px;
  border-top-right-radius: 0;
  border-top-left-radius: 0;
  box-shadow: 0 0 2px 1px var(--gfd-bs);
  opacity: 0;
  z-index: 33;
  backdrop-filter: saturate(180%) blur(18px);
  transform: translateY(-20px);
  transition:
    clip-path 0.2s cubic-bezier(0, 0.55, 0.45, 1),
    transform 0.2s linear,
    opacity 0.1s linear;
  pointer-events: none;
  will-change: transform, opacity;
}

.dropdown__wrapper.--left {
  left: 0;
  right: auto;
  clip-path: inset(0 100% 100% 0 round 6.4px);
}

.dropdown__wrapper.--right {
  right: 0;
  left: auto;
  clip-path: inset(0 0 100% 100% round 6.4px);
}

.dropdown__wrapper.--center {
  right: -250%;
  left: auto;
  clip-path: inset(60px 100% 0 100% round 6.4px);
}

.dropdown__body {
  min-width: 150px;
  max-width: 300px;
  transform: scale(1.06);
  transition: transform 0.15s ease;
  will-change: transform;
}

.dropdown__wrapper-item {
  display: block;
  width: 100%;
}

.dropdown__toggle {
  display: flex;
  align-items: center;
  white-space: nowrap;
  text-decoration: none;
  background-color: transparent;
  border: 0;
  cursor: pointer;
}

.dropdown__toggle:hover {
  color: var(--color-primary);
}

/* .dropdown__toggle::after {
  content: '';
  display: inline-block;
  margin-left: 8px;
  vertical-align: 2px;
  border-top: 4px solid;
  border-right: 4px solid transparent;
  border-bottom: 0;
  border-left: 4px solid transparent;
  transition: transform 0.15s ease;
} */

.dropdown__toggle i,
.dropdown__toggle img {
  pointer-events: none;
}

.dropdown__toggle > img {
  width: var(--gfd-img-size, 25px);
  height: var(--gfd-img-size, 25px);
  border-radius: 100%;
}

.dropdown.--opened .dropdown__wrapper {
  opacity: 1;
  clip-path: inset(-30px -30px -30px -30px round var(--gfd-br, 0.4rem));
  transform: translateY(0);
  transition:
    clip-path 0.2s cubic-bezier(0, 0.55, 0.45, 1),
    transform 0.2s ease,
    opacity 0.1s linear;
  pointer-events: all;
}

.dropdown.--opened .dropdown__body {
  transform: none;
  transition: transform 0.3s cubic-bezier(0.31, 0.48, 0.2, 1.04);
}

.dropdown.--opened .dropdown__toggle::after {
  transform: rotate(180deg);
}

.dropdown.--no-carret .dropdown__toggle::after {
  display: none;
}
</style>
