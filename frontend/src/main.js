import { createApp } from 'vue';
import App from './App.vue';
import { initKeycloak } from './composables/useKeycloak';

const app = createApp(App);
const keycloak = initKeycloak();

// Initialize Keycloak
keycloak
    .init({
        onLoad: 'login-required',
        pkceMethod: 'S256',
        checkLoginIframe: false,
    })
    .then((authenticated) => {
        if (authenticated) {
            console.log('Authenticated with Keycloak');
            console.log('Token:', keycloak.token);

            // Auto refresh the token every 30 seconds if it's about to expire
            setInterval(() => {
                keycloak.updateToken(30).catch(() => {
                    console.warn('Failed to refresh token, logging out...');
                    keycloak.logout();
                });
            }, 20000);

            // Make Keycloak available to components
            app.provide('keycloak', keycloak);
            app.mount('#app');
        } else {
            keycloak.login();
        }
    })
    .catch((err) => console.error('Keycloak init failed', err));
