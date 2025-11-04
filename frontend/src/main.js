import { createApp } from 'vue'
import App from './App.vue'
import { initKeycloak } from './composables/useKeycloak'

const app = createApp(App)
const keycloak = initKeycloak()

keycloak.init({ onLoad: 'login-required' }).then((authenticated) => {
    if (authenticated) {
        console.log('✅ Authenticated with Keycloak')
        app.provide('keycloak', keycloak)
        app.mount('#app')
    } else {
        keycloak.login()
    }
}).catch((err) => console.error('Keycloak init failed', err))
