<template>
  <div>
    <Doughnut :data="data" :options="options" />
  </div>
  <div>
    <Doughnut :data="data" :options="options" />
  </div>
</template>

<script>
import { defineComponent, ref } from 'vue';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js'; // Убираем Plugin
import { Doughnut } from 'vue-chartjs';
ChartJS.register(ArcElement, Tooltip, Legend);

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
    ctx.fillText(label, width / 2, height / 2 + 15);
    ctx.restore();
  },
};

ChartJS.register(centerTextPlugin);

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

    const options = ref({
      responsive: true,
      maintainAspectRatio: false,
      cutout: '75%',
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
          display: true,
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
    });

    return { data, options };
  },
});
</script>

<style scoped>
.axis {
  display: none;
}
</style>
