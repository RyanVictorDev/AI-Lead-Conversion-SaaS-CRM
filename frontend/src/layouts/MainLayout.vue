<template>
  <q-layout view="hHh lpR fFf">
    <q-header elevated class="bg-indigo-9 text-white">
      <q-toolbar>
        <q-toolbar-title>SaaS CRM</q-toolbar-title>
        <q-btn flat dense label="Dashboard" @click="$router.push('/')" />
        <q-btn flat dense label="Leads" @click="$router.push('/leads')" />
        <q-btn flat dense label="Pipeline" @click="$router.push('/pipeline')" />
        <q-btn flat dense label="Conversations" @click="$router.push('/conversations')" />
        <q-space />
        <q-btn flat dense icon="logout" @click="onLogout" />
      </q-toolbar>
    </q-header>

    <q-page-container>
      <slot />
    </q-page-container>
  </q-layout>
</template>

<script setup lang="ts">
import { useAuthStore } from '@/stores/auth';

const auth = useAuthStore();

const onLogout = () => {
  auth.logout();
  window.location.href = '/login';
};
</script>

