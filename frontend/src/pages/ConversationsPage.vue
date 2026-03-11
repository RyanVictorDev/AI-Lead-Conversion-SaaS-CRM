<template>
  <MainLayout>
    <q-page padding class="bg-slate-900 text-white">
      <div class="row q-col-gutter-md" style="height: calc(100vh - 80px)">
        <div class="col-12 col-md-3">
          <q-card class="bg-slate-800 full-height column">
            <q-card-section class="row items-center">
              <div class="col text-subtitle2">Leads</div>
              <div class="col-auto">
                <q-btn dense flat icon="refresh" @click="loadLeads" />
              </div>
            </q-card-section>
            <q-separator dark />
            <q-scroll-area class="col">
              <q-list separator>
                <q-item
                  v-for="lead in leads"
                  :key="lead.id"
                  clickable
                  v-ripple
                  :active="lead.id === selectedLeadId"
                  @click="selectLead(lead.id)"
                >
                  <q-item-section>
                    <q-item-label>{{ lead.name }}</q-item-label>
                    <q-item-label caption>{{ lead.phone }}</q-item-label>
                  </q-item-section>
                </q-item>
              </q-list>
            </q-scroll-area>
          </q-card>
        </div>

        <div class="col-12 col-md-4">
          <q-card class="bg-slate-800 full-height column">
            <q-card-section>
              <div class="text-subtitle2">Conversas</div>
            </q-card-section>
            <q-separator dark />
            <q-card-section v-if="selectedLeadId" class="row items-center q-gutter-sm">
              <q-btn
                dense
                icon="add"
                label="Nova conversa"
                color="indigo-6"
                unelevated
                @click="createConversation"
              />
            </q-card-section>
            <q-scroll-area class="col">
              <q-list separator>
                <q-item
                  v-for="c in conversations"
                  :key="c.id"
                  clickable
                  v-ripple
                  :active="c.id === selectedConversationId"
                  @click="selectConversation(c.id)"
                >
                  <q-item-section>
                    <q-item-label>Conversa #{{ c.id }}</q-item-label>
                    <q-item-label caption>Lead #{{ c.leadId }}</q-item-label>
                  </q-item-section>
                </q-item>
              </q-list>
            </q-scroll-area>
          </q-card>
        </div>

        <div class="col-12 col-md-5">
          <q-card class="bg-slate-800 full-height column">
            <q-card-section class="row items-center">
              <div class="col text-subtitle2">Chat</div>
            </q-card-section>
            <q-separator dark />
            <q-scroll-area class="col q-pa-md">
              <div v-if="!selectedConversationId" class="text-grey-6">
                Selecione uma conversa para ver as mensagens.
              </div>
              <div v-else>
                <div
                  v-for="m in messages"
                  :key="m.id"
                  class="q-mb-sm"
                  :class="m.sender === 'AGENT' ? 'text-right' : 'text-left'"
                >
                  <q-chip
                    :color="m.sender === 'AGENT' ? 'indigo-7' : 'slate-700'"
                    text-color="white"
                    glossy
                  >
                    {{ m.content }}
                  </q-chip>
                </div>
              </div>
            </q-scroll-area>
            <q-separator dark />
            <q-card-section>
              <q-form @submit.prevent="sendMessage" class="row items-center q-col-gutter-sm">
                <div class="col">
                  <q-input
                    v-model="newMessage"
                    dense
                    outlined
                    placeholder="Digite uma mensagem..."
                    :disable="!selectedConversationId"
                  />
                </div>
                <div class="col-auto">
                  <q-btn
                    round
                    icon="send"
                    color="indigo-6"
                    type="submit"
                    :disable="!selectedConversationId || !newMessage"
                  />
                </div>
              </q-form>
            </q-card-section>
          </q-card>
        </div>
      </div>
    </q-page>
  </MainLayout>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import { useQuasar } from 'quasar';
import MainLayout from '@/layouts/MainLayout.vue';
import api from '@/services/api';

interface Lead {
  id: number;
  name: string;
  phone: string;
}

interface Conversation {
  id: number;
  leadId: number;
}

interface Message {
  id: number;
  sender: string;
  content: string;
  createdAt: string;
}

const $q = useQuasar();

const leads = ref<Lead[]>([]);
const conversations = ref<Conversation[]>([]);
const messages = ref<Message[]>([]);

const selectedLeadId = ref<number | null>(null);
const selectedConversationId = ref<number | null>(null);
const newMessage = ref('');

const loadLeads = async () => {
  try {
    const { data } = await api.get<Lead[]>('/api/leads');
    leads.value = data;
  } catch (err) {
    console.error(err);
    $q.notify({ type: 'negative', message: 'Falha ao carregar leads.' });
  }
};

const loadConversations = async () => {
  if (!selectedLeadId.value) return;
  try {
    const { data } = await api.get<Conversation[]>('/api/conversations', {
      params: { leadId: selectedLeadId.value }
    });
    conversations.value = data;
  } catch (err) {
    console.error(err);
    $q.notify({ type: 'negative', message: 'Falha ao carregar conversas.' });
  }
};

const loadMessages = async () => {
  if (!selectedConversationId.value) return;
  try {
    const { data } = await api.get<Message[]>(
      `/api/conversations/${selectedConversationId.value}/messages`
    );
    messages.value = data;
  } catch (err) {
    console.error(err);
    $q.notify({ type: 'negative', message: 'Falha ao carregar mensagens.' });
  }
};

const selectLead = (id: number) => {
  selectedLeadId.value = id;
  selectedConversationId.value = null;
  messages.value = [];
  loadConversations();
};

const selectConversation = (id: number) => {
  selectedConversationId.value = id;
  loadMessages();
};

const createConversation = async () => {
  if (!selectedLeadId.value) return;
  try {
    const { data } = await api.post<Conversation>('/api/conversations', {
      leadId: selectedLeadId.value
    });
    conversations.value.push(data);
    selectedConversationId.value = data.id;
    await loadMessages();
  } catch (err) {
    console.error(err);
    $q.notify({ type: 'negative', message: 'Falha ao criar conversa.' });
  }
};

const sendMessage = async () => {
  if (!selectedConversationId.value || !newMessage.value) return;
  try {
    await api.post(`/api/conversations/${selectedConversationId.value}/messages`, {
      sender: 'AGENT',
      content: newMessage.value
    });
    newMessage.value = '';
    await loadMessages();
  } catch (err) {
    console.error(err);
    $q.notify({ type: 'negative', message: 'Falha ao enviar mensagem.' });
  }
};

// Carregar leads automaticamente ao entrar na página
watch(
  () => true,
  () => {
    loadLeads();
  },
  { immediate: true }
);
</script>

