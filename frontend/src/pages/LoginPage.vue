<template>
  <q-page class="bg-slate-900 flex flex-center">
    <q-card class="q-pa-lg" style="width: 380px; max-width: 90vw">
      <q-card-section>
        <div class="text-h6 text-center q-mb-md">Entrar</div>
      </q-card-section>
      <q-card-section>
        <q-form @submit.prevent="onSubmit" class="q-gutter-md">
          <q-input v-model="email" label="Email" type="email" dense outlined required />
          <q-input v-model="password" label="Senha" type="password" dense outlined required />
          <q-btn
            type="submit"
            label="Entrar"
            color="indigo-7"
            class="full-width q-mt-md"
            unelevated
            :loading="loading"
          />
          <q-btn
            flat
            class="full-width q-mt-sm"
            label="Criar conta"
            @click="$router.push('/register')"
          />
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

const email = ref('');
const password = ref('');
const loading = ref(false);

const auth = useAuthStore();
const router = useRouter();
const $q = useQuasar();

const onSubmit = async () => {
  try {
    loading.value = true;
    const { data } = await api.post('/auth/login', {
      email: email.value,
      password: password.value
    });
    auth.setToken(data.token);
    router.push('/');
  } catch (err) {
    console.error(err);
    $q.notify({
      type: 'negative',
      message: 'Falha ao autenticar. Verifique suas credenciais.'
    });
  } finally {
    loading.value = false;
  }
};
</script>

