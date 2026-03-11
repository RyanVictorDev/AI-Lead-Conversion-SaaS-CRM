import { createApp } from 'vue';
import { createPinia } from 'pinia';
import { Quasar } from 'quasar';
import App from './App.vue';
import router from './router';

import 'quasar/dist/quasar.css';

const app = createApp(App);
app.use(createPinia());
app.use(router);
app.use(Quasar, {});
app.mount('#app');

