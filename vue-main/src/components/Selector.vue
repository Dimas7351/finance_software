<template>
  <div
    class="simple-select"
    :style="{
      '--selector-width': selectorWidth,
    }"
  >
    <div class="select-header" @click="toggleDropdown">
      <span class="select-header__title">{{ selectedLabel || placeholder }}</span>
      <i :class="['arrow', isOpen ? 'up' : 'down']"></i>
    </div>
    <div v-if="isOpen" class="dropdown">
      <ul class="options">
        <li
          v-for="(option, index) in filteredOptions"
          :key="index"
          :class="{ selected: option === selectedValue }"
          @click="selectOption(option)"
        >
          {{ option.title }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { defineComponent, ref, computed, watch } from 'vue';

export default defineComponent({
  name: 'Selector',
  props: {
    options: {
      type: Array,
      required: true,
    },
    modelValue: {
      type: Object,
      default: null,
    },
    placeholder: {
      type: String,
      default: 'Выберите...',
    },
    selectorWidth: {
      type: String,
      default: '200px',
    },
  },
  setup(props, { emit }) {
    const isOpen = ref(false);
    const searchTerm = ref('');
    const selectedValue = ref(props.modelValue);

    const toggleDropdown = () => {
      isOpen.value = !isOpen.value;
    };

    const selectOption = (option) => {
      selectedValue.value = option;
      emit('update:modelValue', option); // передаем полный объект
      isOpen.value = false;
    };

    const filteredOptions = computed(() => {
      const searchTermLower = searchTerm.value.toLowerCase();
      return props.options.filter((option) => option.title.toLowerCase().includes(searchTermLower));
    });

    watch(
      () => props.modelValue,
      (newValue) => {
        selectedValue.value = newValue;
      },
    );

    const selectedLabel = computed(() => selectedValue.value?.title || props.placeholder);

    return {
      isOpen,
      searchTerm,
      selectedValue,
      filteredOptions,
      selectedLabel,
      toggleDropdown,
      selectOption,
    };
  },
});
</script>

<style scoped>
.simple-select {
  position: relative;
  width: var(--selector-width);
  font-family: Roboto, sans-serif;
  font-size: 17px;
}

.select-header {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 8px;
  margin-right: auto;
  background-color: transparent;
  cursor: pointer;
  border: 1px solid var(--input-color-border);
  border-radius: 5px;
}

.select-header__title {
  margin-right: auto;
  margin-left: auto;
}

.arrow {
  transition: transform 0.3s;
  display: flex;
  align-items: center;
  line-height: 1;
}

.arrow::before {
  content: '🢗';
  display: block;
  margin-bottom: -14px;
  text-align: center;
  line-height: 1;
  font-size: 20px;
}

.arrow.up {
  transform: rotate(180deg);
}

.dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  background-color: white;
  border: 1px solid var(--input-color-border);
  border-top: none;
  max-height: 200px;
  overflow-y: auto;
  z-index: 10;
  border-radius: 5px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.search-input {
  width: 100%;
  padding: 8px;
  border: 1px solid var(--input-color-border);
  border-radius: 5px;
  margin-bottom: 5px;
}

.options {
  list-style-type: none;
  margin: 0;
  padding: 0;
}

.options li {
  padding: 8px;
  cursor: pointer;
}

.options li:hover {
  background-color: #f0f0f0;
}

.options li.selected {
  background-color: #e0e0e0;
}
</style>
