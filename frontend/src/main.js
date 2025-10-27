import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import keycloak from './keycloak';

keycloak.init({ onLoad: 'login-required' }).then(authenticated => {
    if (authenticated) {
        const app = createApp(App);
        app.provide('keycloak', keycloak); // ✅ Provide it here
        app.mount('#app');
    } else {
        window.location.reload();
    }
});


