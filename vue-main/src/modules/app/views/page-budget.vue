<script>
import { defineComponent, ref } from 'vue';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Doughnut } from 'vue-chartjs';


const centerTextPlugin = {
  id: 'centerTextPlugin',
  beforeDraw(chart) {
    const ctx = chart.ctx;
    const width = chart.width;
    const height = chart.height;
    const fontSize = 24;
    const label = 'Бюджет';

    ctx.save();
    ctx.font = `${fontSize}px Roboto`;
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';
    ctx.fillStyle = '#000';
    ctx.fillText(label, width / 2, height / 2);
    ctx.restore();
  },
};

ChartJS.register(centerTextPlugin);
ChartJS.register(ArcElement, Tooltip, Legend);

export default defineComponent({
  name: 'PageBudget',
  components: { Doughnut },
  setup() {
    const data = ref({
      labels: ['Еда', 'Транспорт', 'Развлечения', 'Жилищные расходы'],
      datasets: [
        {
          backgroundColor: ['#41B883', '#E46651', '#00D8FF', '#DD1B16'],
          data: [150, 80, 120, 200],
        },
      ],
    });

    const options = ref([
      {
        responsive: true,
        maintainAspectRatio: false,
        cutout: '80%',
        hover: {
          mode: 'nearest',
          animationDuration: 400,
        },
        animation: {
          duration: 1000,
          easing: 'easeInOutQuad',
        },
        plugins: {
          tooltip: {
            enabled: true,
            callbacks: {
              label: function (tooltipItem) {
                let total = 0;
                tooltipItem.chart.data.datasets[0].data.forEach((value) => {
                  total += value;
                });
                const currentValue = tooltipItem.raw;
                const percentage = ((currentValue / total) * 100).toFixed(2);
                return `${tooltipItem.label}: ${currentValue} (${percentage}%)`;
              },
            },
          },
          legend: {
            display: false,
            position: 'top',
            labels: {
              font: {
                size: 10,
                family: 'Roboto',
              },
              padding: 10,
            },
          },
        },
      },

      {
        responsive: true,
        maintainAspectRatio: false,
        cutout: '80%',
        hover: {
          mode: 'nearest',
          animationDuration: 400,
        },
        animation: {
          duration: 1000,
          easing: 'easeInOutQuad',
        },
        plugins: {
          tooltip: {
            enabled: true,
            callbacks: {
              label: function (tooltipItem) {
                let total = 0;
                tooltipItem.chart.data.datasets[0].data.forEach((value) => {
                  total += value;
                });
                const currentValue = tooltipItem.raw;
                const percentage = ((currentValue / total) * 100).toFixed(2);
                return `${tooltipItem.label}: ${currentValue} (${percentage}%)`;
              },
            },
          },
          legend: {
            display: false,
            position: 'top',
            labels: {
              font: {
                size: 10,
                family: 'Roboto',
              },
              padding: 10,
            },
          },
        },
      },
    ]);

    const date = ref({
      from: '',
      to: '',
    });

    const applyMask = (field) => {
      let value = date.value[field];

      value = value.replace(/\D/g, '');

      if (value.length > 2) {
        value = value.substring(0, 2) + '/' + value.substring(2);
      }
      if (value.length > 5) {
        value = value.substring(0, 5) + '/' + value.substring(5);
      }
      value = value.substring(0, 10);

      date.value[field] = value;
    };

    return { data, options, date, applyMask };
  },
});
</script>

<template>
  <div class="budget-wrapper">
    <div class="--flex-row" style="width: 100%; margin: 0 0 30px 0">
      <div style="font-size: 24px; font-weight: 900; margin: 0 20px 0 0">Бюджет</div>
      <div class="--flex-row">
        <input
          @input="applyMask('from')"
          v-model="date.from"
          class="form-input form-input-date"
          placeholder="DD-MM-YYYY"
        />
        <span style="margin: 0 10px">—</span>
        <input
          @input="applyMask('to')"
          v-model="date.to"
          class="form-input form-input-date"
          placeholder="DD-MM-YYYY"
        />
      </div>
    </div>
    <div class="charts-wrapper">
      <div class="budget-wrapper__column --first">
        <div style="margin-bottom: 50px"><Doughnut :data="data" :options="options[0]" /></div>
        <div><Doughnut :data="data" :options="options[0]" /></div>
      </div>
      <div class="budget-wrapper__column --main">
        <div><Doughnut :data="data" :options="options[1]" /></div>
      </div>
      <div class="budget-wrapper__column --second">
        <div style="margin-bottom: 50px"><Doughnut :data="data" :options="options[0]" /></div>
        <div><Doughnut :data="data" :options="options[0]" /></div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.axis {
  display: none;
}
</style>
