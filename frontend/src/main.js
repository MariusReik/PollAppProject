import { createApp } from 'vue';
import App from './App.vue';
import keycloak from './keycloak';
import './style.css';

keycloak.init({ onLoad: 'login-required' })
  .then(authenticated => {
    if (authenticated) {
      console.log('✅ Authenticated with Keycloak');
      localStorage.setItem('token', keycloak.token);

      const app = createApp(App);
      app.provide('keycloak', keycloak);
      app.mount('#app');

      // Auto-refresh token every minute
      setInterval(() => {
        keycloak.updateToken(70).then(refreshed => {
          if (refreshed) {
            console.log('Token refreshed');
            localStorage.setItem('token', keycloak.token);
          }
        }).catch(() => keycloak.logout());
      }, 60000);
    } else {
      console.warn('Not authenticated, reloading...');
      window.location.reload();
    }
  })
  .catch(error => {
    console.error('Keycloak init failed:', error);
  });
