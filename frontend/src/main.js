import { createApp } from 'vue';
import App from './App.vue';
import { initKeycloak } from './composables/useKeycloak.js';

const app = createApp(App);

// Initialize Keycloak before mounting the app
initKeycloak()
    .then((keycloak) => {
        console.log('Authenticated with Keycloak');
        console.log('Token:', keycloak.token);

        // Make Keycloak available globally to Vue components
        app.provide('keycloak', keycloak);
        app.mount('#app');

        // Auto refresh token every 30 seconds
        setInterval(() => {
            keycloak.updateToken(30).catch(() => {
                console.warn('⚠Failed to refresh token, logging out...');
                keycloak.logout();
            });
        }, 20000);
    })
    .catch((err) => {
        console.error('Keycloak init failed', err);
    });
