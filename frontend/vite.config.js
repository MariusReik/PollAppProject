import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

const backendUrl =
    process.env.DOCKER_ENV === 'true'
        ? 'http://pollapp-backend:8080'
        : 'http://localhost:8081'

export default defineConfig({
    plugins: [vue()],
    server: {
        port: 5173,
        host: '0.0.0.0',
        proxy: {
            '/api': {
                target: backendUrl,
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, ''),
            },
        },
    },
})
