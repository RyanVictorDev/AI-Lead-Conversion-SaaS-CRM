<template>
  <MainLayout>
    <q-page padding class="bg-slate-900 text-white">
      <div class="row q-col-gutter-md q-mb-lg">
        <div class="col-12 text-h5">Dashboard</div>
      </div>

      <div class="row q-col-gutter-md q-mb-lg">
        <div class="col-12 col-md-3">
          <q-card class="bg-indigo-9 text-white">
            <q-card-section>
              <div class="text-subtitle2">Total Leads</div>
              <div class="text-h4">{{ summary?.totalLeads ?? '-' }}</div>
            </q-card-section>
          </q-card>
        </div>
        <div class="col-12 col-md-3">
          <q-card class="bg-emerald-9 text-white">
            <q-card-section>
              <div class="text-subtitle2">Taxa de Conversão</div>
              <div class="text-h4">
                {{ summary ? (summary.conversionRate * 100).toFixed(1) + '%' : '-' }}
              </div>
            </q-card-section>
          </q-card>
        </div>
        <div class="col-12 col-md-3">
          <q-card class="bg-sky-9 text-white">
            <q-card-section>
              <div class="text-subtitle2">Mensagens</div>
              <div class="text-h4">{{ summary?.totalMessages ?? '-' }}</div>
            </q-card-section>
          </q-card>
        </div>
        <div class="col-12 col-md-3">
          <q-card class="bg-amber-9 text-white">
            <q-card-section>
              <div class="text-subtitle2">Msgs por Lead</div>
              <div class="text-h4">
                {{ summary ? summary.messagesPerLead.toFixed(1) : '-' }}
              </div>
            </q-card-section>
          </q-card>
        </div>
      </div>
    </q-page>
  </MainLayout>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import MainLayout from '@/layouts/MainLayout.vue';
import api from '@/services/api';

interface AnalyticsSummaryResponse {
  totalLeads: number;
  totalConversions: number;
  conversionRate: number;
  totalMessages: number;
  messagesPerLead: number;
}

const summary = ref<AnalyticsSummaryResponse | null>(null);

const loadSummary = async () => {
  const end = new Date();
  const start = new Date();
  start.setDate(end.getDate() - 30);

  const format = (d: Date) => d.toISOString().slice(0, 10);

  const { data } = await api.get('/api/analytics/summary', {
    params: {
      startDate: format(start),
      endDate: format(end)
    }
  });

  summary.value = data;
};

onMounted(() => {
  loadSummary();
});
</script>

