<template>
  <q-page class="bg-slate-900 flex flex-center">
    <q-card class="q-pa-lg" style="width: 420px; max-width: 95vw">
      <q-card-section>
        <div class="text-h6 text-center q-mb-md">Criar conta</div>
      </q-card-section>
      <q-card-section>
        <q-form @submit.prevent="onSubmit" class="q-gutter-md">
          <q-input v-model="companyName" label="Nome da empresa" dense outlined required />
          <q-input v-model="companyEmail" label="Email da empresa" type="email" dense outlined required />
          <q-select
            v-model="plan"
            :options="plans"
            label="Plano"
            dense
            outlined
            emit-value
            map-options
          />

          <q-separator />

          <q-input v-model="userName" label="Seu nome" dense outlined required />
          <q-input v-model="userEmail" label="Seu email" type="email" dense outlined required />
          <q-input v-model="password" label="Senha" type="password" dense outlined required />

          <q-btn
            type="submit"
            label="Criar conta"
            color="indigo-7"
            class="full-width q-mt-md"
            unelevated
            :loading="loading"
          />
          <q-btn flat class="full-width q-mt-sm" label="Já tenho conta" @click="$router.push('/login')" />
        </q-form>
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useQuasar } from 'quasar';
import api from '@/services/api';
import { useAuthStore } from '@/stores/auth';

const companyName = ref('');
const companyEmail = ref('');
const plan = ref('Starter');
const userName = ref('');
const userEmail = ref('');
const password = ref('');
const loading = ref(false);

const plans = [
  { label: 'Starter', value: 'Starter' },
  { label: 'Pro', value: 'Pro' },
  { label: 'Agency', value: 'Agency' }
];

const auth = useAuthStore();
const router = useRouter();
const $q = useQuasar();

const onSubmit = async () => {
  try {
    loading.value = true;
    const { data } = await api.post('/auth/register', {
      companyName: companyName.value,
      companyEmail: companyEmail.value,
      plan: plan.value,
      userName: userName.value,
      userEmail: userEmail.value,
      password: password.value
    });
    auth.setToken(data.token);
    router.push('/');
  } catch (err) {
    console.error(err);
    $q.notify({
      type: 'negative',
      message: 'Falha ao criar conta. Verifique os dados e tente novamente.'
    });
  } finally {
    loading.value = false;
  }
};
</script>

