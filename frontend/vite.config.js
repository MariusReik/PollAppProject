import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

const isDocker = process.env.VITE_DOCKER_ENV === 'true'

const backendUrl = isDocker
    ? 'http://pollapp-backend:8080'
    : 'http://localhost:8081'

export default defineConfig({
    plugins: [vue()],
    server: {
        host: '0.0.0.0',
        port: 5173,
        proxy: {
            '/api': {
                target: backendUrl,
                changeOrigin: true,
                secure: false,
            },
        },
    },
})
