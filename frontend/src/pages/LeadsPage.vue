<template>
  <MainLayout>
    <q-page padding class="bg-slate-900 text-white">
      <div class="row items-center q-mb-md">
        <div class="col">
          <div class="text-h5">Leads</div>
        </div>
        <div class="col-auto">
          <q-btn color="indigo-7" label="Novo lead" unelevated @click="showCreate = true" />
        </div>
      </div>

      <q-card flat class="bg-slate-800">
        <q-card-section>
          <q-table
            :rows="leads"
            :columns="columns"
            row-key="id"
            flat
            dark
            :loading="loading"
            :pagination="{ rowsPerPage: 10 }"
          >
            <template #body-cell-status="props">
              <q-td :props="props">
                <q-badge
                  :color="statusColor(props.row.status)"
                  class="text-uppercase"
                  v-text="props.row.status"
                />
              </q-td>
            </template>
          </q-table>
        </q-card-section>
      </q-card>

      <q-dialog v-model="showCreate" persistent>
        <q-card style="min-width: 360px">
          <q-card-section>
            <div class="text-h6">Novo lead</div>
          </q-card-section>
          <q-card-section>
            <q-form @submit.prevent="createLead" class="q-gutter-md">
              <q-input v-model="form.name" label="Nome" dense outlined required />
              <q-input v-model="form.phone" label="Telefone (WhatsApp)" dense outlined required />
              <q-input v-model="form.email" label="Email" dense outlined type="email" />
              <div class="row justify-end q-gutter-sm q-mt-md">
                <q-btn flat label="Cancelar" color="grey-6" v-close-popup />
                <q-btn
                  label="Salvar"
                  color="indigo-7"
                  type="submit"
                  :loading="saving"
                  unelevated
                />
              </div>
            </q-form>
          </q-card-section>
        </q-card>
      </q-dialog>
    </q-page>
  </MainLayout>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useQuasar } from 'quasar';
import MainLayout from '@/layouts/MainLayout.vue';
import api from '@/services/api';

interface Lead {
  id: number;
  name: string;
  phone: string;
  email: string | null;
  status: string;
}

const $q = useQuasar();

const leads = ref<Lead[]>([]);
const loading = ref(false);
const showCreate = ref(false);
const saving = ref(false);

const form = ref({
  name: '',
  phone: '',
  email: ''
});

const columns = [
  { name: 'name', label: 'Nome', field: 'name', align: 'left', sortable: true },
  { name: 'phone', label: 'Telefone', field: 'phone', align: 'left' },
  { name: 'email', label: 'Email', field: 'email', align: 'left' },
  { name: 'status', label: 'Status', field: 'status', align: 'left' }
];

const statusColor = (status: string) => {
  switch (status) {
    case 'NEW':
      return 'grey-8';
    case 'CONTACTED':
      return 'indigo-6';
    case 'QUALIFIED':
      return 'emerald-6';
    case 'LOST':
      return 'red-6';
    case 'CONVERTED':
      return 'teal-6';
    default:
      return 'grey-7';
  }
};

const loadLeads = async () => {
  loading.value = true;
  try {
    const { data } = await api.get<Lead[]>('/api/leads');
    leads.value = data;
  } catch (err) {
    console.error(err);
    $q.notify({ type: 'negative', message: 'Falha ao carregar leads.' });
  } finally {
    loading.value = false;
  }
};

const createLead = async () => {
  try {
    saving.value = true;
    await api.post('/api/leads', {
      name: form.value.name,
      phone: form.value.phone,
      email: form.value.email
    });
    showCreate.value = false;
    form.value = { name: '', phone: '', email: '' };
    await loadLeads();
    $q.notify({ type: 'positive', message: 'Lead criado com sucesso.' });
  } catch (err) {
    console.error(err);
    $q.notify({ type: 'negative', message: 'Falha ao criar lead.' });
  } finally {
    saving.value = false;
  }
};

onMounted(loadLeads);
</script>

