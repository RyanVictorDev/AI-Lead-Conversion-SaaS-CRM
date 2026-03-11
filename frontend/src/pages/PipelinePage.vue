<template>
  <MainLayout>
    <q-page padding class="bg-slate-900 text-white">
      <div class="row items-center q-mb-md">
        <div class="col">
          <div class="text-h5">Pipeline</div>
          <div class="text-subtitle2 text-grey-5">
            Kanban das etapas do funil (visualização inicial).
          </div>
        </div>
      </div>

      <q-card flat class="bg-slate-800 q-pa-md">
        <div v-if="loading" class="text-center q-pa-lg">
          <q-spinner color="indigo-5" size="lg" />
        </div>

        <div v-else-if="!pipeline">
          <div class="text-grey-5">
            Nenhum pipeline configurado para esta empresa.
          </div>
        </div>

        <div v-else class="row q-col-gutter-md no-wrap scroll">
          <div
            v-for="stage in pipeline.stages"
            :key="stage.id"
            class="col-12 col-sm-6 col-md-3"
          >
            <q-card class="bg-slate-900 column" style="min-height: 220px">
              <q-card-section class="bg-slate-800">
                <div class="text-subtitle2 text-uppercase">{{ stage.name }}</div>
              </q-card-section>
              <q-separator dark />
              <q-card-section>
                <div class="text-grey-6 text-caption">
                  Arraste leads entre colunas (funcionalidade a ser ligada ao backend).
                </div>
              </q-card-section>
            </q-card>
          </div>
        </div>
      </q-card>
    </q-page>
  </MainLayout>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import MainLayout from '@/layouts/MainLayout.vue';
import api from '@/services/api';
import { useQuasar } from 'quasar';

interface Stage {
  id: number;
  name: string;
  order: number;
}

interface Pipeline {
  id: number;
  name: string;
  stages: Stage[];
}

const $q = useQuasar();
const pipeline = ref<Pipeline | null>(null);
const loading = ref(false);

const loadPipeline = async () => {
  loading.value = true;
  try {
    const { data } = await api.get<Pipeline>('/api/pipelines/default');
    pipeline.value = data;
  } catch (err) {
    console.error(err);
    $q.notify({ type: 'negative', message: 'Falha ao carregar pipeline.' });
  } finally {
    loading.value = false;
  }
};

onMounted(loadPipeline);
</script>

